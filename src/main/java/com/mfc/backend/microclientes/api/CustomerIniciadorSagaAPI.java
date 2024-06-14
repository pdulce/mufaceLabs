package com.mfc.backend.microclientes.api;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.api.usecases.CreacionCustomerIniciadorSagaUseCase;
import com.mfc.infra.controller.ArqBaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerIniciadorSagaAPI extends ArqBaseRestController {
    @Autowired(required=false)
    CreacionCustomerIniciadorSagaUseCase creacionCustomerIniciadorSagaUseCase;

    @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody @NotNull CustomerDTO customerDTO) {
        return this.creacionCustomerIniciadorSagaUseCase.ejecutar("application-Id-sample",
                this.orchestratorManager, customerDTO, this.messageSource);
    }

}
