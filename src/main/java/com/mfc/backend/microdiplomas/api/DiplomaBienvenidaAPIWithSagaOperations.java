package com.mfc.backend.microdiplomas.api;


import com.mfc.backend.microdiplomas.domain.model.command.Diploma;
import com.mfc.backend.microdiplomas.domain.service.command.DiplomaCommandStepSagaAdapter;
import com.mfc.infra.controller.BaseRestController;
import com.mfc.infra.event.Event;
import com.mfc.infra.utils.ConstantMessages;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping(value = "customer")
public class DiplomaBienvenidaAPIWithSagaOperations extends BaseRestController {
    @Autowired
    DiplomaCommandStepSagaAdapter diplomaCommandStepSagaAdapter;

    @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Diploma create(@RequestBody @NotNull Diploma diploma) {
        Locale locale = "es" != null ? new Locale("es") : Locale.getDefault();
        String message = this.messageSource.getMessage(ConstantMessages.SUCCESS_CREATED, null, locale);
        diploma.setId(UUID.randomUUID().getMostSignificantBits());
        Event<?> event = this.orchestratorManager.startSaga(diplomaCommandStepSagaAdapter.getSagaName(),
                diplomaCommandStepSagaAdapter.getTypeOrOperation(), diploma);
        return (Diploma) event.getInnerEvent().getData();

    }


}
