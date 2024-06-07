package com.mfc.infra.saga.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import com.mfc.infra.saga.port.SagaOrchestratorPort;
import com.mfc.infra.saga.port.SagaStepPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public abstract class SagaStepAdapter implements SagaStepPort {

    Logger logger = LoggerFactory.getLogger(SagaStepAdapter.class);

    protected static final String GROUP_ID = "saga-step-group";
    @Autowired
    CommandEventPublisherPort commandEventPublisherPort;

    @KafkaListener(topics = SagaOrchestratorPort.SAGA_DO_STEP_OPERATION_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        if (event.getSagaStepInfo().isDoCompensateOp()) {
            doSagaCompensation(event);
            this.commandEventPublisherPort.publish(event.getSagaStepInfo().getSagaName() + "-" +
                    SagaOrchestratorPort.SAGA_FROM_STEP_TOPIC + event.getSagaStepInfo().getStepNumber()
                    + "-topic", event);
            logger.info("Se ha informado al orchestrator que la operación de compensación en el step "
                    + event.getSagaStepInfo().getStepNumber()
                    + " para la transacción núm: " + event.getSagaStepInfo().getTransactionIdentifier()
                    + " se ha realizado de forma satisfactoria");
        } else {
            doSagaOperation(event);
            this.commandEventPublisherPort.publish(event.getSagaStepInfo().getSagaName() + "-" +
                    SagaOrchestratorPort.SAGA_FROM_STEP_TOPIC + event.getSagaStepInfo().getStepNumber()
                    + "-topic", event);
            logger.info("Se ha informado al orchestrator que la operación de consolidación en el step "
                    + event.getSagaStepInfo().getStepNumber()
                    + " para la transacción núm: " + event.getSagaStepInfo().getTransactionIdentifier()
                    + " se ha realizado de forma satisfactoria");
        }
    }

    public abstract Integer getOrderStepInSaga();

    @Override
    public abstract void doSagaOperation(Event<?> event);

    @Override
    public abstract void doSagaCompensation(Event<?> event);

}
