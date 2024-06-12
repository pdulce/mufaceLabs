package com.mfc.infra.output.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import com.mfc.infra.output.port.GenericRepositoryPort;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.output.port.SagaStepPort;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Transactional
public abstract class CommandServiceStepSagaAdapter<T, ID> extends CommandServiceAdapter<T, ID>
        implements SagaStepPort<T, ID> {

    protected Logger logger = LoggerFactory.getLogger(CommandServiceStepSagaAdapter.class);
    @Autowired(required = false)
    protected CommandEventPublisherPort commandEventPublisherPort;

    @Autowired
    protected GenericRepositoryPort<T, ID> repository;

    /** metodos para conectar con transacciones distribuidas bajo el patrón SAGA **/

    /****
     Las clases Service que extienda de CommandStepSagaAdapter deberán implementar el método listen, cada una con un
     group-id diferente:

     protected static final String GROUP_ID = "saga-step-group-<texto-libre>-<secuencial>";

        @KafkaListener(topics = <topic_Step> groupId = <SU_GROUP_ID>)
        public void listen(Event event) {
            super.procesarEvento(event);
        }
     ***/

    public void processStepEvent(Event event) {
        if (!applicationDefinedProperties.isActive()) {
            logger.error("Debe tener activa la configuración de uso de mensajería en la arquitectura");
            return;
        }
        event.getSagaStepInfo().setStepNumber(getOrderStepInSaga());
        if (event.getSagaStepInfo().isDoCompensateOp()) {
            orderSagaCompensation(event);
            this.commandEventPublisherPort.publish(SagaOrchestratorPort.SAGA_FROM_STEP_TOPIC, event);
            logger.info("Se ha informado al orchestrator que la operación de compensación en el step "
                    + event.getSagaStepInfo().getStepNumber()
                    + " para la transacción núm: " + event.getSagaStepInfo().getTransactionIdentifier()
                    + (event.getSagaStepInfo().getStateOfOperation() == Event.SAGA_OPE_FAILED
                    ? " ha fallado" : " se ha realizado de forma satisfactoria"));
        } else {
            event.getSagaStepInfo().setLastStep(isLastStepInSaga());
            orderSagaOperation(event);
            this.commandEventPublisherPort.publish(SagaOrchestratorPort.SAGA_FROM_STEP_TOPIC, event);
            logger.info("Se ha informado al orchestrator que la operación de consolidación en el step "
                    + event.getSagaStepInfo().getNextStepNumberToProccess()
                    + " para la transacción núm: " + event.getSagaStepInfo().getTransactionIdentifier()
                    + (event.getSagaStepInfo().getStateOfOperation() == Event.SAGA_OPE_FAILED
                    ? " ha fallado" : " se ha realizado de forma satisfactoria"));
        }

    }
    private void orderSagaOperation(Event event) {
        //invocamos a la implementación específica del service del microservicio
        Object newdata = getNewData(getWrapper(event));
        try{
            newdata = this.doSagaOperation(event);
            event.setId(Event.STEP_ID_PREFIX + getOrderStepInSaga());
            event.getSagaStepInfo().setStateOfOperation(Event.SAGA_OPE_SUCCESS);
        } catch (ConstraintViolationException exc) {
            event.getSagaStepInfo().setStateOfOperation(Event.SAGA_OPE_FAILED);
            String msg = "doSagaOperation failed: Cause " + exc.getLocalizedMessage();
            event.getSagaStepInfo().setErrorMsgOperation(msg);
            logger.error(msg);
        } catch (Throwable exc) {
            event.getSagaStepInfo().setStateOfOperation(Event.SAGA_OPE_FAILED);
            String msg = "doSagaOperation failed: Cause " + exc.getLocalizedMessage();
            event.getSagaStepInfo().setErrorMsgOperation(msg);
            logger.error(msg);
        } finally {
            event.getInnerEvent().setNewData(newdata);
        }
    }

    private void orderSagaCompensation(Event event) {
        //invocamos a la implementación específica del service del microservicio
        Object newdata = getNewData(getWrapper(event));
        try {
            newdata = this.doSagaCompensation(event);
            event.getSagaStepInfo().setStateOfCompensation(Event.SAGA_OPE_SUCCESS);
        } catch (Throwable exc) {
            event.getSagaStepInfo().setStateOfCompensation(Event.SAGA_OPE_FAILED);
            String msg = "doSagaOperation failed: Cause " + exc.getLocalizedMessage();
            event.getSagaStepInfo().setErrorMsgCompensation(msg);
            logger.error(msg);
        } finally {
            event.getInnerEvent().setNewData(newdata);
        }
    }

    @Override
    public abstract Object doSagaOperation(Event event) throws Throwable;

    @Override
    public abstract Object doSagaCompensation(Event event) throws Throwable;

    @Override
    public abstract String getSagaName();

    @Override
    public abstract int getOrderStepInSaga();

    @Override
    public abstract boolean isLastStepInSaga();

    @Override
    public abstract String getTypeOrOperation();

    protected abstract Object getNewData(Object dataReceivedFromPreviousStep);

    protected abstract Object getWrapper(Event event);


}
