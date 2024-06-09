package com.mfc.infra.output.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.event.SagaStepInfo;
import com.mfc.infra.input.port.EventConsumer;
import com.mfc.infra.input.port.EventStoreInputPort;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.SagaStepComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SagaOrchestratorAdapter<T> implements SagaOrchestratorPort<T>, EventConsumer {

    Logger logger = LoggerFactory.getLogger(SagaOrchestratorPort.class);
    private static final String GROUP_ID = "saga-orchestrator-group";

    @Autowired
    CommandEventPublisherPort commandEventPublisherPort;
    @Autowired
    EventStoreInputPort eventStoreConsumerAdapter;

    /**
     * El almacén es el nombre de la saga, pueden coexistir varias sagas en una misma aplicación
     * El identificador sirve de forma de agrupar todos las step-transactions, será nuestro agregado en Redis
     * @param sagaName
     * @param data
     * @return
     */
    public Event<?> startSaga(String sagaName, String operation, T data) {

        Long transactionIdentifier = Math.abs(UUID.randomUUID().getMostSignificantBits());

        Event<?> dataEvent = new Event(sagaName, "author", "applicationId-999",
                String.valueOf(transactionIdentifier), operation, data);

        // Al iniciar la Saga el orquestador asigna un id único a la transacc. distribuida

        SagaStepInfo sagaInfo = new SagaStepInfo(sagaName, 1, transactionIdentifier);
        dataEvent.setSagaStepInfo(sagaInfo);

        // Iniciar Saga
        this.commandEventPublisherPort.publish(DO_OPERATION + "-" + dataEvent.getSagaStepInfo().getSagaName() +
                "-" + dataEvent.getSagaStepInfo().getLastStepNumberProccessed(), dataEvent);

        logger.info("Saga iniciada con número de transacción: " + transactionIdentifier);

        return dataEvent;
    }

    @KafkaListener(topics = SAGA_FROM_STEP_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        // Aquí tenemos decidir en función del mensaje que mande el step antes invocado, si continuar con la siguiente
        // operación de la SAGA o mandar compensar los steps anteriores
        if (event.getSagaStepInfo().getLastStepNumberProccessed() == Event.SAGA_OPE_FAILED
                && event.getSagaStepInfo().getNextStepNumberToProccess() > 1) {
            event.getSagaStepInfo().setNextStepNumberToProccess(event.getSagaStepInfo().
                    getLastStepNumberProccessed() - 1);
            backToStepToCompensate(event.getSagaStepInfo().getSagaName(),
                    event.getSagaStepInfo().getNextStepNumberToProccess(),
                    event.getSagaStepInfo().getTransactionIdentifier());
        } else {
            // puede ser que sea un mensaje de la finalización de una compensación de una operación de consolidación
            if (event.getSagaStepInfo().isDoCompensateOp()) {
                // Ordenamos compensar al (step - 1) respecto al que llega como evento en el bus
                // SIEMPRE que no hayamos llegado al principio de la saga
                if ((event.getSagaStepInfo().getLastStepNumberProccessed() - 1) > 1) {
                    event.getSagaStepInfo().setNextStepNumberToProccess(event.getSagaStepInfo().
                            getLastStepNumberProccessed() - 1);
                    backToStepToCompensate(event.getSagaStepInfo().getSagaName(),
                            event.getSagaStepInfo().getNextStepNumberToProccess(),
                            event.getSagaStepInfo().getTransactionIdentifier());
                }
            } else {
                // guardamos en Redis el step-transac.obj. cuando tengamos la seguridad de que la operación fue OK
                if (!event.getSagaStepInfo().isLastStep()) {
                    event.getSagaStepInfo().setNextStepNumberToProccess(event.getSagaStepInfo().
                            getLastStepNumberProccessed() + 1);
                }
                this.eventStoreConsumerAdapter.saveEvent(event.getSagaStepInfo().getSagaName(),
                        String.valueOf(event.getSagaStepInfo().getTransactionIdentifier()), event);
                if (!event.getSagaStepInfo().isLastStep()) {
                    continueNextStep(event);
                }
            }
        }

    }

    /*** METODOS PRIVADOS ***/
    private void continueNextStep(Event<?> event) {
        // Iniciar Saga
        this.commandEventPublisherPort.publish(DO_OPERATION + "-"
                + event.getSagaStepInfo().getSagaName() + "-" + event.getSagaStepInfo().getNextStepNumberToProccess(),
                event);

        logger.info("Saga continuada con step " + event.getSagaStepInfo().getNextStepNumberToProccess()
                + " para la transacción núm.: " + event.getSagaStepInfo().getTransactionIdentifier());
    }

    private void backToStepToCompensate(String sagaName, Integer stepNumber, Long transactionIdentifier) {

        Event<?> eventoTransaccion = searchPreviousStepTransaction(sagaName, stepNumber, transactionIdentifier);
        if (eventoTransaccion != null) {
            eventoTransaccion.getSagaStepInfo().setDoCompensateOp(true);
            this.commandEventPublisherPort.publish(
                    DO_OPERATION + "-" + eventoTransaccion.getSagaStepInfo().getSagaName() +
                    "-" + eventoTransaccion.getSagaStepInfo().getLastStepNumberProccessed(), eventoTransaccion);
            logger.info("Solicitada operación de compensación en step " + stepNumber
                    + " para la transacción núm: " + transactionIdentifier);
        } else {
            logger.error("No se ha localizado la transacción de la saga " + sagaName + " step " + stepNumber
                    + " para la transacc. núm: " + transactionIdentifier + " para poder compensar esa step-transacc.");
        }
    }

    private Event<?> searchPreviousStepTransaction(String sagaName, Integer stepNumber, Long transactionIdentifier) {

        List<Object> objetosTransaccionados = eventStoreConsumerAdapter.
                findById(sagaName, String.valueOf(transactionIdentifier));
        // ordenamos esta lista del step más reciente al primero (stepnumber: 1)
        Collections.sort(objetosTransaccionados, new SagaStepComparator());
        // recorremos la lista y vamos mandando ejecutar las operaciones de compensación de los steps anteriores
        // (se mandan al bus event para que las consuma/atienda el service-consumer de cada step de esta saga)
        AtomicReference<Event<?>> objetoSearched = null;
        objetosTransaccionados.forEach((data) -> {
            Event<?> event = (Event<?>) data;
            if (event.getSagaStepInfo().getLastStepNumberProccessed() == stepNumber.intValue()) {
                objetoSearched.set(event);
            }
        });

        return objetoSearched != null ? objetoSearched.get() : null;

    }


}
