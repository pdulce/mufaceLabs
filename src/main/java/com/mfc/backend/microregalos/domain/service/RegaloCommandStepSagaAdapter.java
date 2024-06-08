package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.domain.model.DiplomaWrapper;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.adapter.CommandStepSagaAdapter;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConversionUtils;
import jakarta.validation.ConstraintViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class RegaloCommandStepSagaAdapter extends CommandStepSagaAdapter<Regalo> {

    private static final String SAGA_NAME = "sagaBienvenidaCustomer";
    private static final int SAGA_STEP_NUMBER = 3;
    private static final String TOPIC_FOR_ME = SagaOrchestratorPort.DO_OPERATION + "-" + SAGA_NAME + "-" + SAGA_STEP_NUMBER;
    private static final String GROUP_ID = "saga-step-group-diplomaconsumer-service-step-3";

    public String getDocumentEntityClassname() {
        return "RegaloDocument";
    } // este micro no usa CQRS

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
        return true;
    }

    @Override
    public String getTypeOrOperation() {
        return Event.EVENT_TYPE_CREATE;
    }

    @Override
    public void doSagaOperation(Event<?> event) {
        try {
            DiplomaWrapper customer = ConversionUtils.
                    convertMapToObject((LinkedHashMap<String, Object>) event.getInnerEvent().getData(),
                            DiplomaWrapper.class);
            Regalo regalo = new Regalo();
            regalo.setName(customer.getName());
            regalo.setIdcustomer(customer.getId());
            if (customer.getCountry() != null) {
                if (customer.getCountry() == null || "".contentEquals(customer.getCountry())) {
                    throw new Throwable ("forzando exception en SAGA step 2 para probar la arquitectura");
                }
                if (customer.getCountry().contentEquals("Afganistán")) {
                    throw new Throwable ("Error de negocio: " +
                            "exception por país FORBIDDEN en SAGA step 2 para probar la arquitectura");
                }
                regalo.setRegion(customer.getCountry());
            }
            regalo.setTitulo("Diploma de Bienvenida, señor(a) " + customer.getName());

            this.insert(regalo);
            event.getInnerEvent().setNewData(regalo);
        } catch (ConstraintViolationException exc) {
            event.getSagaStepInfo().setLastStepNumberProccessed(Event.SAGA_OPE_FAILED);
            logger.error("doSagaOperation failed: Cause ", exc.getLocalizedMessage());
        } catch (Throwable exc) {
            event.getSagaStepInfo().setLastStepNumberProccessed(Event.SAGA_OPE_FAILED);
            logger.error("doSagaOperation failed: Cause ", exc);
        }
    }

    @Override
    public void doSagaCompensation(Event<?> event) {
        try {
            Regalo diploma = ConversionUtils.
                    convertMapToObject((LinkedHashMap<String, Object>) event.getInnerEvent().getData(),
                            Regalo.class);
            this.delete(diploma);
        } catch (Throwable notExistException) {
            event.getSagaStepInfo().setLastStepNumberProccessed(Event.SAGA_OPE_FAILED);
            logger.error("doSagaCompensation failed: Cause ", notExistException);
        }
    }


}
