package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.repository.CustomerCommandRepositoryPort;
import com.mfc.infra.domain.DTOConverter;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.adapter.RelationalServiceStepSagaAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class CustomerRelationalStepSagaAdapterService extends RelationalServiceStepSagaAdapter<Customer, CustomerDTO, Long> {
    private static final String SAGA_NAME = "sagaBienvenidaCustomer";
    private static final int SAGA_STEP_NUMBER = 1;
    private static final String APP_ID = "application-Id-sample";
    private static final String TOPIC_FOR_ME = SagaOrchestratorPort.DO_OPERATION + APP_ID + "-" + SAGA_NAME + "-"
            + SAGA_STEP_NUMBER;
    private static final String GROUP_ID = "saga-step-group-customerconsumer-service-step-1";

    protected CustomerCommandRepositoryPort repository;

    @Autowired
    public CustomerRelationalStepSagaAdapterService(CustomerCommandRepositoryPort customerCommandRepositoryPortP) {
        this.repository = customerCommandRepositoryPortP;
    }

    protected GenericRepositoryPort<Customer, Long> getRepository() {
        return this.repository;
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
    protected Object getWrapper(Event event) {
        return event;
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
    public Object doSagaOperation(Event event) {
        Customer customer = ConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Customer.class);
        CustomerDTO dto = this.crear(DTOConverter.convertToDTO(customer, getClassOfDTO()));
        return DTOConverter.convertToEntity(dto, getClassOfEntity());
    }

    @Override
    public Object doSagaCompensation(Event event) {
        Customer customer = ConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Customer.class);
        this.borrar(DTOConverter.convertToDTO(customer, getClassOfDTO()));
        return customer;
    }



}
