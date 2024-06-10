package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.domain.model.DiplomaWrapper;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.adapter.CommandStepSagaAdapter;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConversionUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RegaloCommandStepSagaAdapter extends CommandStepSagaAdapter<Regalo> {

    private static final String SAGA_NAME = "sagaBienvenidaCustomer";
    private static final int SAGA_STEP_NUMBER = 3;
    private static final String TOPIC_FOR_ME = SagaOrchestratorPort.DO_OPERATION + "-" + SAGA_NAME + "-" + SAGA_STEP_NUMBER;
    private static final String GROUP_ID = "saga-step-group-regaloconsumer-service-step-3";

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
    public Object doSagaOperation(Event<?> event) {
        DiplomaWrapper customer = ConversionUtils.convertMapToObject(event.getInnerEvent().getData(),
                DiplomaWrapper.class);
        Regalo regalo = getRegalo(customer);
        return this.insert(regalo);
    }

    private static Regalo getRegalo(DiplomaWrapper diploma) {
        Regalo regalo = new Regalo();
        regalo.setTexto_tarjeta("¡¡Disfrute de su tarjeta regalo, " + diploma.getName() + "!!");
        regalo.setCustomerid(diploma.getIdcustomer());
        if (diploma.getTitulo() != null) {
            regalo.setColor_caja(diploma.getTitulo().contentEquals("Juan") ? "Blanco-azul" : "Verde");
        }
        regalo.setValor_bono_regalo(new BigDecimal(50)); //50 euros
        return regalo;
    }

    @Override
    public void doSagaCompensation(Event<?> event) {
        Regalo regalo = ConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Regalo.class);
        this.delete(regalo);
    }


}
