package com.mfc.infra.output.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.output.port.SagaStepPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;


@Transactional
public abstract class CommandStepSagaAdapter<T> extends CommandAdapter<T> implements SagaStepPort {

    protected Logger logger = LoggerFactory.getLogger(CommandStepSagaAdapter.class);
    @Autowired
    protected CommandEventPublisherPort commandEventPublisherPort;

    @Autowired
    protected JpaRepository<T, Long> repository;

    /** metodos para conectar con transacciones distribuidas bajo el patrón SAGA **/

    /****
     Las clases Service que extienda de CommandStepSagaAdapter deberán implementar el método listen, cada una con un
     group-id diferente:

     protected static final String GROUP_ID = "saga-step-group-<texto-libre>-<secuencial>";

        @KafkaListener(topics = <topic_Step> groupId = <SU_GROUP_ID>)
        public void listen(Event<?> event) {
            super.procesarEvento(event);
        }
     ***/

    public void processStepEvent(Event<?> event) {

        if (event.getSagaStepInfo().isDoCompensateOp()) {
            orderSagaCompensation(event);
            if (event.getSagaStepInfo().getStateOfFinalization() != Event.SAGA_OPE_FAILED) {
                event.getSagaStepInfo().setStateOfFinalization(Event.SAGA_OPE_SUCCESS);
            }
            this.commandEventPublisherPort.publish(SagaOrchestratorPort.SAGA_FROM_STEP_TOPIC, event);
            logger.info("Se ha informado al orchestrator que la operación de compensación en el step "
                    + event.getSagaStepInfo().getNextStepNumberToProccess()
                    + " para la transacción núm: " + event.getSagaStepInfo().getTransactionIdentifier()
                    + (event.getSagaStepInfo().getStateOfFinalization() == Event.SAGA_OPE_FAILED
                    ? " ha fallado" : " se ha realizado de forma satisfactoria"));
        } else {
            event.getSagaStepInfo().setLastStep(isLastStepInSaga());
            orderSagaOperation(event);
            if (event.getSagaStepInfo().getStateOfFinalization() != Event.SAGA_OPE_FAILED) {
                event.getSagaStepInfo().setStateOfFinalization(Event.SAGA_OPE_SUCCESS);
            }
            this.commandEventPublisherPort.publish(SagaOrchestratorPort.SAGA_FROM_STEP_TOPIC, event);
            logger.info("Se ha informado al orchestrator que la operación de consolidación en el step "
                    + event.getSagaStepInfo().getNextStepNumberToProccess()
                    + " para la transacción núm: " + event.getSagaStepInfo().getTransactionIdentifier()
                    + (event.getSagaStepInfo().getStateOfFinalization() == Event.SAGA_OPE_FAILED
                    ? " ha fallado" : " se ha realizado de forma satisfactoria"));
        }

    }
    private void orderSagaOperation(Event<?> event) {
        //invocamos a la implementación específica del service del microservicio
        this.doSagaOperation(event);
    }

    private void orderSagaCompensation(Event<?> event) {
        //invocamos a la implementación específica del service del microservicio
        this.doSagaCompensation(event);
    }

    @Override
    public abstract void doSagaOperation(Event<?> event);

    @Override
    public abstract void doSagaCompensation(Event<?> event);

    @Override
    public abstract String getSagaName();

    @Override
    public abstract int getOrderStepInSaga();

    @Override
    public abstract boolean isLastStepInSaga();

    @Override
    public abstract String getTypeOrOperation();






}
