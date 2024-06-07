package com.mfc.backend.microcustomers.domain.service.command;

import com.mfc.backend.microcustomers.domain.model.command.Customer;
import com.mfc.infra.event.Event;
import com.mfc.infra.exceptions.NotExistException;
import com.mfc.infra.output.adapter.CommandAdapter;
import com.mfc.infra.output.adapter.CommandStepSagaAdapter;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommandStepSagaAdapter extends CommandStepSagaAdapter<Customer> {

    public static final String GROUP_ID = "saga-step-group-customerconsumer-service-step-1";

    public String getDocumentEntityClassname() {
        return "CustomerDocument";
    }

    @Override
    @KafkaListener(topics = SagaOrchestratorPort.SAGA_ORDER_OPERATION_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        super.processStepEvent(event);
    }

    @Override
    public String getSagaName() {
        return "misaga";
    }

    @Override
    public int getOrderStepInSaga() {
        return 1;
    }

    @Override
    public String getTypeOrOperation() {
        return Event.EVENT_TYPE_CREATE;
    }

    @Override
    public void doSagaOperation(Event<?> event) {
        try {
            this.insert((Customer) event.getInnerEvent().getData());
        } catch (Throwable exc) {
            event.getInnerEvent().setTypeEvent(Event.EVENT_FAILED_OPERATION);
            logger.error("doSagaOperation failed: Cause ", exc);
        }
    }

    @Override
    public void doSagaCompensation(Event<?> event) {
        try {
            this.delete((Customer) event.getInnerEvent().getData());
        } catch (NotExistException notExistException) {
            logger.error("doSagaCompensation failed: Cause ", notExistException);
            event.getInnerEvent().setTypeEvent(Event.EVENT_FAILED_OPERATION);
        }
    }


}
