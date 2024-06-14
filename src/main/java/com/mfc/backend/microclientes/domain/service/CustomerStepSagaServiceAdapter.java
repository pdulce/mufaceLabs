package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.repository.CustomerCommandRepositoryPort;
import com.mfc.infra.dto.ArqAbstractDTO;
import com.mfc.infra.event.ArqEvent;
import com.mfc.infra.output.adapter.ArqRelationalServiceStepArqSagaAdapter;
import com.mfc.infra.output.port.ArqSagaOrchestratorPort;
import com.mfc.infra.utils.ArqConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class CustomerStepSagaServiceAdapter extends ArqRelationalServiceStepArqSagaAdapter<Customer, CustomerDTO, Long> {
    private static final String SAGA_NAME = "sagaBienvenidaCustomer";
    private static final int SAGA_STEP_NUMBER = 1;
    private static final String APP_ID = "application-Id-sample";
    private static final String TOPIC_FOR_ME = ArqSagaOrchestratorPort.DO_OPERATION + APP_ID + "-" + SAGA_NAME + "-"
            + SAGA_STEP_NUMBER;
    private static final String GROUP_ID = "saga-step-group-customerconsumer-service-step-1";

    protected CustomerCommandRepositoryPort repository;

    @Autowired
    public CustomerStepSagaServiceAdapter(CustomerCommandRepositoryPort customerCommandRepositoryPortP) {
        this.repository = customerCommandRepositoryPortP;
    }

    protected JpaRepository<Customer, Long> getRepository() {
        return this.repository;
    }

    @KafkaListener(topics = TOPIC_FOR_ME, groupId = GROUP_ID)
    public void listen(ArqEvent<?> event) {
        // llega una orden del orchestrator
        super.processStepEvent(event);
    }

    @Override
    protected Object getNewData(Object customer) {
        return customer;
    }

    @Override
    protected Object getWrapper(ArqEvent event) {
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
        return ArqEvent.EVENT_TYPE_CREATE;
    }

    @Override
    public Object doSagaOperation(ArqEvent event) {
        Customer customer = ArqConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Customer.class);
        CustomerDTO dto = this.crear(ArqAbstractDTO.convertToDTO(customer, getClassOfDTO()));
        return ArqAbstractDTO.convertToEntity(dto, getClassOfEntity());
    }

    @Override
    public Object doSagaCompensation(ArqEvent event) {
        Customer customer = ArqConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Customer.class);
        this.borrar(ArqAbstractDTO.convertToDTO(customer, getClassOfDTO()));
        return customer;
    }



}
