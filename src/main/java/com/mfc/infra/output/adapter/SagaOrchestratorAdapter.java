package com.mfc.infra.output.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.event.SagaStepInfo;
import com.mfc.infra.input.port.EventConsumer;
import com.mfc.infra.input.port.EventStoreInputPort;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConstantMessages;
import com.mfc.infra.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@ConditionalOnProperty(name = "arch.eventbroker.active", havingValue = "true", matchIfMissing = false)
public class SagaOrchestratorAdapter<T> implements SagaOrchestratorPort<T>, EventConsumer {

    Logger logger = LoggerFactory.getLogger(SagaOrchestratorPort.class);
    private static final String GROUP_ID = "saga-orchestrator-group";

    @Autowired
    CommandEventPublisherPort commandEventPublisher;
    @Autowired
    EventStoreInputPort eventStoreConsumer;

    /**
     * El almacén es el nombre de la saga, pueden coexistir varias sagas en una misma aplicación
     * El identificador sirve de forma de agrupar todos las step-transactions, será nuestro agregado en Redis
     * @param sagaName
     * @param data
     * @return
     */
    public Event startSaga(String sagaName, String operation, T data) {

        Long transactionIdentifier = Math.abs(UUID.randomUUID().getMostSignificantBits());

        Event<?> dataEvent = new Event(sagaName, "author", "applicationId-999", "step-1",
                operation, data);

        // Al iniciar la Saga el orquestador asigna un id único a la transacc. distribuida

        SagaStepInfo sagaInfo = new SagaStepInfo(sagaName, 1, transactionIdentifier);
        dataEvent.setSagaStepInfo(sagaInfo);

        // Iniciar Saga
        this.commandEventPublisher.publish(DO_OPERATION + "-" + dataEvent.getSagaStepInfo().getSagaName() +
                "-1", dataEvent);

        logger.info("Saga iniciada con número de transacción: " + transactionIdentifier);

        return dataEvent;
    }

    @KafkaListener(topics = SAGA_FROM_STEP_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        // Aquí tenemos decidir en función del mensaje que mande el step antes invocado, si continuar con la siguiente
        // operación de la SAGA o mandar compensar los steps anteriores

        if (event.getSagaStepInfo().isDoCompensateOp()) { // se ha ejecutado operación de compensación
            // actualizamos la entrada de eeste evento de transacción de la operación previa
            actualizarEventoConCompensacion(event);
            if (event.getSagaStepInfo().getStepNumber() > 1) {
                backToStepToCompensate(event);
            }
        } else {
            // ingresamos el evento de esta step-transaction en el agregado de esta transacción
            this.eventStoreConsumer.saveEvent(event.getSagaStepInfo().getSagaName(),
                    String.valueOf(event.getSagaStepInfo().getTransactionIdentifier()), event);

            if (event.getSagaStepInfo().getStateOfOperation() == Event.SAGA_OPE_FAILED) {
                if (event.getSagaStepInfo().getStepNumber() > 1) {
                    backToStepToCompensate(event);
                }
            } else {
                // guardamos en Redis el step-transac.obj. cuando tengamos la seguridad de que la operación fue OK
                event.getSagaStepInfo().setNextStepNumberToProccess(event.getSagaStepInfo().
                        getNextStepNumberToProccess() + (event.getSagaStepInfo().isLastStep() ? 0 : 1));
                if (!event.getSagaStepInfo().isLastStep()) {
                    continueNextStep(event);
                } else {
                    logger.info("Saga con número de transacción: " + event.getSagaStepInfo().getTransactionIdentifier()
                            + ", finalizada de forma exitosa");
                }
            }
        }
    }

    public String getLastStateOfTansactionInSaga(String saganame, String transaccId) {
        List<Object> objetos = this.eventStoreConsumer.findById(saganame, transaccId);
        if (objetos == null || objetos.isEmpty()) {
            return ConstantMessages.ERROR_NOT_FOUND;
        }
        boolean failedSaga = false;
        boolean lastFinished = false;
        int i = 0;
        Event event = null;
        while (!lastFinished && !failedSaga && i < objetos.size()) {
            event = ConversionUtils.convertMapToObject(objetos.get(i++), Event.class);
            failedSaga = event.getSagaStepInfo().getStateOfOperation() == Event.SAGA_OPE_FAILED;
            if (event.getSagaStepInfo().isLastStep()) {
                lastFinished = true;
            }
        }
        return getString(lastFinished, failedSaga, event);
    }

    /*** METODOS PRIVADOS ***/

    private static String getString(boolean lastFinished, boolean failedSaga, Event event) {
        String msgKey = ConstantMessages.DISTRIBUTED_FIN_STATE_OK;
        boolean running = !lastFinished && !failedSaga;
        if (running) {
            // revisamos si se trata de un caso de transacción compensada
            if (event != null && event.getSagaStepInfo().isDoCompensateOp() &&
                    event.getSagaStepInfo().getStateOfOperation() == Event.SAGA_OPE_SUCCESS) {
                msgKey = ConstantMessages.DISTRIBUTED_FIN_STATE_KO;
            } else {
                msgKey = ConstantMessages.DISTRIBUTED_RUNNING;
            }
        } else if (failedSaga) {
            msgKey = ConstantMessages.DISTRIBUTED_FIN_STATE_KO;
        }
        return msgKey;
    }

    private void continueNextStep(Event<?> event) {
        // Iniciar Saga
        this.commandEventPublisher.publish(DO_OPERATION + "-"
                + event.getSagaStepInfo().getSagaName() + "-" + event.getSagaStepInfo().getNextStepNumberToProccess(),
                event);

        logger.info("Saga continuada con step " + event.getSagaStepInfo().getNextStepNumberToProccess()
                + " para la transacción núm.: " + event.getSagaStepInfo().getTransactionIdentifier());
    }

    private void actualizarEventoConCompensacion(Event event) {
        Event eventoTransaccion = searchStepInTransaction(event.getSagaStepInfo().getSagaName(),
                event.getSagaStepInfo().getStepNumber(), event.getSagaStepInfo().getTransactionIdentifier());
        if (eventoTransaccion != null) {
            eventoTransaccion.setSagaStepInfo(event.getSagaStepInfo());
            this.eventStoreConsumer.update(event.getSagaStepInfo().getSagaName(),
                    String.valueOf(event.getSagaStepInfo().getTransactionIdentifier()),
                    Event.STEP_ID_PREFIX + event.getSagaStepInfo().getStepNumber(), event);
        }
    }

    private void backToStepToCompensate(Event event) {
        Event eventoTransaccion = searchStepInTransaction(event.getSagaStepInfo().getSagaName(),
                event.getSagaStepInfo().getStepNumber() - 1,
                event.getSagaStepInfo().getTransactionIdentifier());
        if (eventoTransaccion != null) {
            eventoTransaccion.getSagaStepInfo().setDoCompensateOp(true);
            this.commandEventPublisher.publish(
                    DO_OPERATION + "-" + eventoTransaccion.getSagaStepInfo().getSagaName() +
                    "-" + eventoTransaccion.getSagaStepInfo().getStepNumber(), eventoTransaccion);
            logger.info("Solicitada operación de compensación en step "
                    + eventoTransaccion.getSagaStepInfo().getStepNumber()
                    + " para la transacción núm: " + eventoTransaccion.getSagaStepInfo().getTransactionIdentifier());
        } else {
            logger.error("No se ha localizado la transacción de la saga " + event.getSagaStepInfo().getSagaName()
                    + " step " + event.getSagaStepInfo().getNextStepNumberToProccess()
                    + " para la transacc. núm: " + event.getSagaStepInfo().getTransactionIdentifier()
                    + " para poder compensar esa step-transacc.");
        }
    }

    private Event searchStepInTransaction(String sagaName, Integer stepNumber, Long transactionIdentifier) {
        List<Object> objetosTransaccionados = this.eventStoreConsumer.
                findById(sagaName, String.valueOf(transactionIdentifier));
        if (objetosTransaccionados == null || objetosTransaccionados.isEmpty()) {
            return null;
        }
        // recorremos la lista y vamos mandando ejecutar las operaciones de compensación de los steps anteriores
        // (se mandan al bus event para que las consuma/atienda el service-consumer de cada step de esta saga)
        AtomicReference<Event<?>> objetoSearched = new AtomicReference<>();
        objetosTransaccionados.forEach((data) -> {
            Event event = ConversionUtils.convertMapToObject(data, Event.class);
            if (event.getSagaStepInfo().getStepNumber() == stepNumber.intValue()) {
                objetoSearched.set(event);
            }
        });
        return objetoSearched != null ? objetoSearched.get() : null;
    }


}
