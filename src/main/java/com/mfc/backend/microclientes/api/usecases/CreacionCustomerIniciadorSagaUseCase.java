package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.service.CustomerArqArqRelationalStepArqSagaAdapterService;
import com.mfc.infra.event.ArqEvent;
import com.mfc.infra.output.port.ArqSagaOrchestratorPort;
import com.mfc.infra.utils.ArqConstantMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CreacionCustomerIniciadorSagaUseCase {

    @Autowired(required = false)
    CustomerArqArqRelationalStepArqSagaAdapterService customerCommandStepSagaAdapter;

    public String ejecutar(String applicationId, ArqSagaOrchestratorPort orchestratorManager, CustomerDTO customerDTO,
                           MessageSource messageSource) {
        if (orchestratorManager == null || this.customerCommandStepSagaAdapter == null) {
            throw new RuntimeException("Saga no puede iniciarse: arch.eventbroker.active está a false");
        }
        Customer customer = new Customer(customerDTO);
        Locale locale = "es" != null ? new Locale("es") : Locale.getDefault();
        ArqEvent event = orchestratorManager.startSaga(applicationId,
                customerCommandStepSagaAdapter.getSagaName(),
                customerCommandStepSagaAdapter.getTypeOrOperation(), customer);
        String message = messageSource.getMessage(ArqConstantMessages.DISTRIBUTED_INITIADED,
                new Object[]{event == null ? "" : event.getSagaStepInfo().getSagaName(),
                    String.valueOf(event == null ? "" : event.getSagaStepInfo().getTransactionIdentifier())}, locale);
        return event != null ? message : null;
    }


}
