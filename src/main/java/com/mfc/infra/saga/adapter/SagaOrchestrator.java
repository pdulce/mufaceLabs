package com.mfc.infra.saga.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.event.SagaStepInfo;
import com.mfc.infra.input.port.EventConsumer;
import com.mfc.infra.input.port.EventStoreInputPort;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import com.mfc.infra.saga.port.SagaOrchestratorPort;
import com.mfc.infra.utils.SagaStepComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SagaOrchestrator implements SagaOrchestratorPort, EventConsumer {

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
    public Long startSaga(String sagaName, Event<?> data) {
        // Al iniciar la Saga el orquestador asigna un id único a la transacc. distribuida
        Long transactionIdentifier = UUID.randomUUID().getMostSignificantBits();
        SagaStepInfo sagaInfo = new SagaStepInfo(sagaName, 1, transactionIdentifier);
        data.setSagaStepInfo(sagaInfo);
        // guardamos el objeto transaccional en Redis
        this.eventStoreConsumerAdapter.saveEvent(sagaName, String.valueOf(transactionIdentifier), data);

        // Iniciar Saga
        this.commandEventPublisherPort.publish(sagaName + "-" + DO_OPERATION_TOPIC_NAME + "1-topic", data);

        logger.info("Saga iniciada con número de transacción: " + transactionIdentifier);
        return transactionIdentifier;
    }

    @KafkaListener(topics = SAGA_FROM_STEP_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        // Aquí tenemos decidir en función del mensaje que mande el step antes invocado, si continuar con la siguiente
        // operación de la SAGA o mandar compensar los steps anteriores
        if (event.getInnerEvent().getTypeEvent().contentEquals(Event.EVENT_FAILED_OPERATION)) {
            sendCompensationsFromLastSuccesfulStep(event.getSagaStepInfo().getSagaName(),
                    event.getSagaStepInfo().getTransactionIdentifier());
        } else {
            // puede ser que sea un mensaje de la finalización de una compensación de una operación de consolidación
            if (event.getSagaStepInfo().isDoCompensateOp()) {
                // nothing TODO:
                // Ya hemos ordenado antes a todos los services de la SAGA que deshagan sus operac. de consolidacion
            } else {
                continueNextStep(event.getSagaStepInfo().getSagaName(),
                        event.getSagaStepInfo().getStepNumber(),
                        event, event.getSagaStepInfo().getTransactionIdentifier());
            }

        }

    }

    /*** METODOS PRIVADOS ***/
    private void continueNextStep(String sagaName, Integer stepNumber, Event<?> data, Long transactionIdentifier) {
        // Buscamos el identificador de transacción justo en el objeto persistido en step anterior
        SagaStepInfo sagaInfo = new SagaStepInfo(sagaName, stepNumber, transactionIdentifier);
        data.setSagaStepInfo(sagaInfo);
        // guardamos el objeto transaccional en Redis
        this.eventStoreConsumerAdapter.saveEvent(sagaName, String.valueOf(transactionIdentifier), data);

        // Iniciar Saga
        this.commandEventPublisherPort.publish(sagaName + "-" + SAGA_DO_STEP_OPERATION_TOPIC + stepNumber
                + "-topic", data);

        logger.info("Saga continuada con step " + stepNumber + " para la transacción núm.: " + transactionIdentifier);
    }

    private void sendCompensationsFromLastSuccesfulStep(String sagaName, Long transactionIdentifier) {

        List<Object> objetosTransaccionados = eventStoreConsumerAdapter.
                findById(sagaName, String.valueOf(transactionIdentifier));
        // ordenamos esta lista del step más reciente al primero (stepnumber: 1)
        Collections.sort(objetosTransaccionados, new SagaStepComparator());
        // recorremos la lista y vamos mandando ejecutar las operaciones de compensación de los steps anteriores
        // (se mandan al bus event para que las consuma/atienda el service-consumer de cada step de esta saga)

        objetosTransaccionados.forEach((data) -> {
            Event<?> event = (Event<?>) data;
            event.getSagaStepInfo().setDoCompensateOp(true);
            this.commandEventPublisherPort.publish(sagaName + "-" +  SAGA_DO_STEP_OPERATION_TOPIC
                    + event.getSagaStepInfo().getStepNumber() + "-topic", event);
            logger.info("Solicitada operación de compensación en step " + event.getSagaStepInfo().getStepNumber()
                    + " para la transacción núm: " + transactionIdentifier);
        });

        logger.info("Solicitados a todos los steps anteriores de esta transacción núm: " + transactionIdentifier
                + " la compensación de dicha transacción");

    }



}
