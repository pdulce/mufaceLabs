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
    CommandEventPublisherPort commandEventPublisherPort;
    @Autowired
    protected JpaRepository<T, Long> repository;

    /** metodos para conectar con transacciones distribuidas bajo el patrón SAGA **/

    /****
     Las clases Service que extienda de CommandStepSagaAdapter deberán implementar el método listen, cada una con un
     group-id diferente:

     protected static final String GROUP_ID = "saga-step-group-<texto-libre>-<secuencial>";

        @KafkaListener(topics = SagaOrchestratorPort.SAGA_DO_STEP_OPERATION_TOPIC groupId = SU_GROUP_ID)
        public void listen(Event<?> event) {
            super.procesarEvento(event);
        }
     ***/
    public abstract void listen(Event<?> event);

    public void processStepEvent(Event<?> event) {
        // hay que ver si desechar este mensaje por si pertenece a otra saga y/o a otro step que no sea esta instancia
        if (!event.getSagaStepInfo().getSagaName().contentEquals(getSagaName())
                || event.getSagaStepInfo().getStepNumber() != getOrderStepInSaga()) {
            return; // mensaje descartado
        }

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

    @Override
    public abstract String getSagaName();

    @Override
    public abstract int getOrderStepInSaga();

    @Override
    public abstract void doSagaOperation(Event<?> event);

    @Override
    public abstract void doSagaCompensation(Event<?> event);

}
