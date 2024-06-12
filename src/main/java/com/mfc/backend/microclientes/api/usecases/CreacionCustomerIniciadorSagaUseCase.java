package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.service.CustomerCommandStepSagaAdapterService;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConstantMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CreacionCustomerIniciadorSagaUseCase {

    @Autowired(required = false)
    CustomerCommandStepSagaAdapterService customerCommandStepSagaAdapter;

    public String ejecutar(String applicationId, SagaOrchestratorPort orchestratorManager, CustomerDTO customerDTO,
                           MessageSource messageSource) {
        if (orchestratorManager == null || this.customerCommandStepSagaAdapter == null) {
            throw new RuntimeException("Saga no puede iniciarse: arch.eventbroker.active está a false");
        }
        Customer customer = new Customer(customerDTO);
        Locale locale = "es" != null ? new Locale("es") : Locale.getDefault();
        Event event = orchestratorManager.startSaga(applicationId,
                customerCommandStepSagaAdapter.getSagaName(),
                customerCommandStepSagaAdapter.getTypeOrOperation(), customer);
        String message = messageSource.getMessage(ConstantMessages.DISTRIBUTED_INITIADED,
                new Object[]{event == null ? "" : event.getSagaStepInfo().getSagaName(),
                    String.valueOf(event == null ? "" : event.getSagaStepInfo().getTransactionIdentifier())}, locale);
        return event != null ? message : null;
    }


}
