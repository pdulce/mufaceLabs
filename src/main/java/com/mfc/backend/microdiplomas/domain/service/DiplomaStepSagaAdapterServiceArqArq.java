package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.domain.model.CustomerWrapper;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.repository.DiplomaCommandRepository;
import com.mfc.infra.domain.ArqDTOConverter;
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
public class DiplomaStepSagaAdapterServiceArqArq extends ArqRelationalServiceStepArqSagaAdapter<Diploma, DiplomaDTO, Long> {

    private static final String SAGA_NAME = "sagaBienvenidaCustomer";
    private static final int SAGA_STEP_NUMBER = 2;
    private static final String APP_ID = "application-Id-sample";
    private static final String TOPIC_FOR_ME = ArqSagaOrchestratorPort.DO_OPERATION + APP_ID + "-" + SAGA_NAME + "-"
            + SAGA_STEP_NUMBER;
    private static final String GROUP_ID = "saga-step-group-diplomaconsumer-service-step-2";

    protected DiplomaCommandRepository repository;

    @Autowired
    public DiplomaStepSagaAdapterServiceArqArq(DiplomaCommandRepository dplomaCommandRepository) {
        this.repository = dplomaCommandRepository;
    }

    protected JpaRepository<Diploma, Long> getRepository() {
        return this.repository;
    }

    @KafkaListener(topics = TOPIC_FOR_ME , groupId = GROUP_ID)
    public void listen(ArqEvent<?> event) {
        // llega una orden del orchestrator
        super.processStepEvent(event);
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
    public Object doSagaOperation(ArqEvent event) throws Throwable {
        CustomerWrapper customer = (CustomerWrapper) getWrapper(event);
        Diploma diploma = (Diploma) getNewData(customer);
        if (customer.getCountry() == null || "".contentEquals(customer.getCountry())) {
            throw new Throwable ("forzando exception en SAGA step 2 para probar la arquitectura");
        }
        if (customer.getCountry().contentEquals("Afganistán")) {
            throw new Throwable ("Error de negocio: " +
                    "exception por país FORBIDDEN en SAGA step 2 para probar la arquitectura");
        }
        return this.crear(ArqDTOConverter.convertToDTO(diploma, DiplomaDTO.class));
    }

    @Override
    public Object doSagaCompensation(ArqEvent event) {
        Diploma diploma = ArqConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Diploma.class);
        this.borrar(ArqDTOConverter.convertToDTO(diploma, DiplomaDTO.class));
        return diploma;
    }

    @Override
    protected Object getNewData(Object customer) {
        Diploma diploma = new Diploma();
        diploma.setName(((CustomerWrapper)customer).getName());
        diploma.setIdcustomer(((CustomerWrapper)customer).getId());
        diploma.setRegion(((CustomerWrapper)customer).getCountry());
        diploma.setTitulo("Diploma de Bienvenida, señor(a) " + ((CustomerWrapper)customer).getName());
        return diploma;
    }

    @Override
    protected Object getWrapper(ArqEvent event) {
        CustomerWrapper customer = ArqConversionUtils.convertMapToObject(event.getInnerEvent().getData(),
                CustomerWrapper.class);
        return customer;
    }

}
