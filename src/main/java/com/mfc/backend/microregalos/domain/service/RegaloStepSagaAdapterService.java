package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.DiplomaWrapper;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.repository.RegaloCommandRepository;
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

import java.math.BigDecimal;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class RegaloStepSagaAdapterService extends ArqRelationalServiceStepArqSagaAdapter<Regalo, RegaloDTO, Long> {

    private static final String SAGA_NAME = "sagaBienvenidaCustomer";
    private static final int SAGA_STEP_NUMBER = 3;
    private static final String APP_ID = "application-Id-sample";
    private static final String TOPIC_FOR_ME = ArqSagaOrchestratorPort.DO_OPERATION + APP_ID + "-" + SAGA_NAME + "-"
            + SAGA_STEP_NUMBER;
    private static final String GROUP_ID = "saga-step-group-regaloconsumer-service-step-3";

    protected RegaloCommandRepository repository;

    @Autowired
    public RegaloStepSagaAdapterService(RegaloCommandRepository regaloCommandRepository) {
        this.repository = regaloCommandRepository;
    }

    protected JpaRepository<Regalo, Long> getRepository() {
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
        return true;
    }

    @Override
    public String getTypeOrOperation() {
        return ArqEvent.EVENT_TYPE_CREATE;
    }

    @Override
    public Object doSagaOperation(ArqEvent event) {
        DiplomaWrapper customer = (DiplomaWrapper) getWrapper(event);
        Regalo regalo = (Regalo) getNewData(customer);
        return this.crear(ArqAbstractDTO.convertToDTO(regalo, RegaloDTO.class));
    }

    @Override
    public Object doSagaCompensation(ArqEvent event) {
        Regalo regalo = ArqConversionUtils.convertMapToObject(event.getInnerEvent().getData(), Regalo.class);
        this.borrar(ArqAbstractDTO.convertToDTO(regalo, RegaloDTO.class));
        return regalo;
    }

    protected Object getNewData(Object diploma) {
        Regalo regalo = new Regalo();
        regalo.setTexto_tarjeta("¡¡Disfrute de su tarjeta regalo, " + ((DiplomaWrapper)diploma).getName() + "!!");
        regalo.setCustomerid(((DiplomaWrapper)diploma).getIdcustomer());
        if (((DiplomaWrapper)diploma).getTitulo() != null) {
            regalo.setColor_caja(((DiplomaWrapper)diploma).getTitulo().contentEquals("Juan")
                    ? "Blanco-azul" : "Verde");
        }
        regalo.setValor_bono_regalo(new BigDecimal(50)); //50 euros
        return regalo;
    }

    @Override
    protected Object getWrapper(ArqEvent event) {
        DiplomaWrapper customer = ArqConversionUtils.convertMapToObject(event.getInnerEvent().getData(),
                DiplomaWrapper.class);
        return customer;
    }


}
