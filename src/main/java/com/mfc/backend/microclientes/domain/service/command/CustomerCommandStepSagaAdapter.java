package com.mfc.backend.microclientes.domain.service.command;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.adapter.CommandStepSagaAdapter;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConversionUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommandStepSagaAdapter extends CommandStepSagaAdapter<Customer> {

    private static final String SAGA_NAME = "sagaBienvenidaCustomer";
    private static final int SAGA_STEP_NUMBER = 1;
    private static final String TOPIC_FOR_ME = SagaOrchestratorPort.DO_OPERATION + "-" + SAGA_NAME + "-"
            + SAGA_STEP_NUMBER;
    private static final String GROUP_ID = "saga-step-group-customerconsumer-service-step-1";

    public String getDocumentEntityClassname() {
        return "CustomerDocument";
    }

    @KafkaListener(topics = TOPIC_FOR_ME, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        // llega una orden del orchestrator
        super.processStepEvent(event);
    }

    @Override
    protected Object getNewData(Object customer) {
        return customer;
    }

    @Override
    public String getSagaName() {
        return SAGA_NAME;
    }

    @Override
    public int getOrderStepInSaga() {
        return SAGA_STEP_NUMBER;
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
    public Object doSagaOperation(Event<?> event) {
        Customer customer = ConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Customer.class);
        return this.insert(customer);
    }

    @Override
    public void doSagaCompensation(Event<?> event) {
        Customer customer = ConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Customer.class);
        this.delete(customer);
    }


}
