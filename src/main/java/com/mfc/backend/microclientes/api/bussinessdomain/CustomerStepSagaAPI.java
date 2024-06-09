package com.mfc.backend.microclientes.api.bussinessdomain;


import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.backend.microclientes.domain.service.command.CustomerCommandStepSagaAdapter;
import com.mfc.infra.controller.BaseRestController;
import com.mfc.infra.event.Event;
import com.mfc.infra.utils.ConstantMessages;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping(value = "customer")
public class CustomerStepSagaAPI extends BaseRestController {

    @Autowired
    CustomerCommandStepSagaAdapter customerCommandStepSagaAdapter;


    @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer create(@RequestBody @NotNull Customer customer) {
        Locale locale = "es" != null ? new Locale("es") : Locale.getDefault();
        String message = this.messageSource.getMessage(ConstantMessages.SUCCESS_CREATED, null, locale);
        Event<?> event = this.orchestratorManager.startSaga(customerCommandStepSagaAdapter.getSagaName(),
                customerCommandStepSagaAdapter.getTypeOrOperation(), customer);
        return event != null ? (Customer) event.getInnerEvent().getData() : null;

    }



}
