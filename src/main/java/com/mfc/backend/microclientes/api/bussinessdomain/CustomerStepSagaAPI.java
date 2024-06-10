package com.mfc.backend.microclientes.api.bussinessdomain;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.backend.microclientes.domain.service.command.CustomerCommandStepSagaAdapter;
import com.mfc.infra.controller.BaseRestController;
import com.mfc.infra.event.Event;
import com.mfc.infra.utils.ConstantMessages;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@ConditionalOnProperty(name = "arch.eventbroker.active", havingValue = "true", matchIfMissing = false)
@RequestMapping(value = "customer")
public class CustomerStepSagaAPI extends BaseRestController {
    @Autowired
    CustomerCommandStepSagaAdapter customerCommandStepSagaAdapter;
    @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody @NotNull Customer customer) {
        Locale locale = "es" != null ? new Locale("es") : Locale.getDefault();
        Event event = this.orchestratorManager.startSaga(customerCommandStepSagaAdapter.getSagaName(),
                customerCommandStepSagaAdapter.getTypeOrOperation(), customer);
        String message = this.messageSource.getMessage(ConstantMessages.DISTRIBUTED_INITIADED,
                new Object[]{event == null ? "" : event.getSagaStepInfo().getSagaName(),
                    String.valueOf(event == null ? "" : event.getSagaStepInfo().getTransactionIdentifier())}, locale);
        return event != null ? message : null;
    }

    @GetMapping(value = "getTransactionFinalState")
    @Override
    public String getSagaEstadoFinalizacion(@RequestParam String saga, @RequestParam String transaccionId) {
        return super.getSagaEstadoFinalizacion(saga, transaccionId);
    }


}
