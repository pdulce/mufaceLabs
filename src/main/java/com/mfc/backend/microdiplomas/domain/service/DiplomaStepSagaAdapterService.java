package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.domain.model.CustomerWrapper;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.repository.DiplomaCommandRepository;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.adapter.CommandServiceStepSagaAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class DiplomaStepSagaAdapterService extends CommandServiceStepSagaAdapter<Diploma, Long> {

    private static final String SAGA_NAME = "sagaBienvenidaCustomer";
    private static final int SAGA_STEP_NUMBER = 2;
    private static final String APP_ID = "application-Id-sample";
    private static final String TOPIC_FOR_ME = SagaOrchestratorPort.DO_OPERATION + APP_ID + "-" + SAGA_NAME + "-"
            + SAGA_STEP_NUMBER;
    private static final String GROUP_ID = "saga-step-group-diplomaconsumer-service-step-2";

    protected DiplomaCommandRepository repository;

    @Autowired
    public DiplomaStepSagaAdapterService(DiplomaCommandRepository dplomaCommandRepository) {
        this.repository = dplomaCommandRepository;
    }

    protected GenericRepositoryPort<Diploma, Long> getRepository() {
        return this.repository;
    }

    @KafkaListener(topics = TOPIC_FOR_ME , groupId = GROUP_ID)
    public void listen(Event<?> event) {
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
        return Event.EVENT_TYPE_CREATE;
    }

    @Override
    public Object doSagaOperation(Event event) throws Throwable {
        CustomerWrapper customer = (CustomerWrapper) getWrapper(event);
        Diploma diploma = (Diploma) getNewData(customer);
        if (customer.getCountry() == null || "".contentEquals(customer.getCountry())) {
            throw new Throwable ("forzando exception en SAGA step 2 para probar la arquitectura");
        }
        if (customer.getCountry().contentEquals("Afganistán")) {
            throw new Throwable ("Error de negocio: " +
                    "exception por país FORBIDDEN en SAGA step 2 para probar la arquitectura");
        }
        return this.crear(diploma);
    }

    @Override
    public Object doSagaCompensation(Event event) {
        Diploma diploma = ConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Diploma.class);
        this.borrar(diploma);
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
    protected Object getWrapper(Event event) {
        CustomerWrapper customer = ConversionUtils.convertMapToObject(event.getInnerEvent().getData(),
                CustomerWrapper.class);
        return customer;
    }

}
