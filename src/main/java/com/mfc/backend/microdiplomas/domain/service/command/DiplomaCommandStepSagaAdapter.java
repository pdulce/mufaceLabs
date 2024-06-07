package com.mfc.backend.microdiplomas.domain.service.command;

import com.mfc.backend.microdiplomas.domain.model.command.CustomerWrapper;
import com.mfc.backend.microdiplomas.domain.model.command.Diploma;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.adapter.CommandStepSagaAdapter;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConversionUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

@Service
public class DiplomaCommandStepSagaAdapter extends CommandStepSagaAdapter<Diploma> {

    public static final String GROUP_ID = "saga-step-group-diplomaconsumer-service-step-2";

    public String getDocumentEntityClassname() {
        return "DiplomaDocument";
    } // este micro no usa CQRS

    @KafkaListener(topics = SagaOrchestratorPort.SAGA_ORDER_OPERATION_TOPIC, groupId = GROUP_ID)
    @Override
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
        return 2;
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
            CustomerWrapper customer = ConversionUtils.
                    convertMapToObject((LinkedHashMap<String, Object>) event.getInnerEvent().getData(),
                            CustomerWrapper.class);
            Diploma diploma = new Diploma();
            diploma.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
            diploma.setName(customer.getName());
            diploma.setIdcustomer(customer.getId());
            if (customer.getCountry() != null) {
                if (customer.getCountry() == null || "".contentEquals(customer.getCountry())) {
                    throw new Throwable ("forzando exception en SAGA step 2 para probar la arquitectura");
                }
                if (customer.getCountry().contentEquals("Afganistán")) {
                    throw new Throwable ("Error de negocio: " +
                            "exception por país FORBIDDEN en SAGA step 2 para probar la arquitectura");
                }
                diploma.setRegion(customer.getCountry());
            }
            diploma.setTitulo("Bienvenida diploma, señor(a) " + customer.getName());

            this.insert(diploma);
            event.getInnerEvent().setNewData(diploma);
        } catch (Throwable exc) {
            event.getInnerEvent().setTypeEvent(Event.EVENT_FAILED_OPERATION);
            logger.error("doSagaOperation failed: Cause ", exc);
        }
    }

    @Override
    public void doSagaCompensation(Event<?> event) {
        try {
            Diploma diploma = ConversionUtils.
                    convertMapToObject((LinkedHashMap<String, Object>) event.getInnerEvent().getData(),
                            Diploma.class);
            this.delete(diploma);
        } catch (Throwable notExistException) {
            event.getInnerEvent().setTypeEvent(Event.EVENT_FAILED_OPERATION);
            logger.error("doSagaCompensation failed: Cause ", notExistException);
        }
    }


}
