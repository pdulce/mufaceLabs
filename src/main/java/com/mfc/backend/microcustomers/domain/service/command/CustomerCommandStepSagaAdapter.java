package com.mfc.backend.microcustomers.domain.service.command;

import com.mfc.backend.microcustomers.domain.model.command.Customer;
import com.mfc.infra.event.Event;
import com.mfc.infra.exceptions.NotExistException;
import com.mfc.infra.output.adapter.CommandAdapter;
import com.mfc.infra.output.adapter.CommandStepSagaAdapter;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConversionUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.UUID;

@Service
public class CustomerCommandStepSagaAdapter extends CommandStepSagaAdapter<Customer> {

    public static final String GROUP_ID = "saga-step-group-customerconsumer-service-step-1";

    public String getDocumentEntityClassname() {
        return "CustomerDocument";
    }

    @Override
    @KafkaListener(topics = SagaOrchestratorPort.SAGA_ORDER_OPERATION_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        // llega una orden del orchestrator
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
    public boolean isLastStepInSaga() {
        return false;
    }
    @Override
    public String getTypeOrOperation() {
        return Event.EVENT_TYPE_CREATE;
    }

    @Override
    public void doSagaOperation(Event<?> event) {
        try {
            Customer customer = ConversionUtils.
                    convertMapToObject((LinkedHashMap<String, Object>) event.getInnerEvent().getData(), Customer.class);
            if (customer.getId() == null) {
                customer.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
            }
            this.insert(customer);
        } catch (Throwable exc) {
            event.getSagaStepInfo().setLastStepNumberProccessed(Event.SAGA_OPE_FAILED);
            logger.error("doSagaOperation failed: Cause ", exc);
        }
    }

    @Override
    public void doSagaCompensation(Event<?> event) {
        try {
            Customer customer = ConversionUtils.
                    convertMapToObject((LinkedHashMap<String, Object>) event.getInnerEvent().getData(), Customer.class);
            this.delete(customer);
        } catch (NotExistException notExistException) {
            event.getSagaStepInfo().setLastStepNumberProccessed(Event.SAGA_OPE_FAILED);
            logger.error("doSagaCompensation failed: Cause ", notExistException);
        }
    }


}
