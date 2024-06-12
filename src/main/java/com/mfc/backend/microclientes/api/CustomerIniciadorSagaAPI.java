package com.mfc.backend.microclientes.api;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.api.usecases.CreacionCustomerUseCase;
import com.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerIniciadorSagaAPI extends BaseRestController {
    @Autowired(required=false)
    CreacionCustomerUseCase creacionCustomerUseCase;

    @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody @NotNull CustomerDTO customerDTO) {
        return creacionCustomerUseCase.ejecutar(this.orchestratorManager, customerDTO, this.messageSource);
    }

    @GetMapping(value = "getTransactionFinalState")
    public String getSagaEstadoFinalizacion(@RequestParam String saga, @RequestParam String transaccionId) {
        return super.getSagaEstadoFinalizacion("application-Id", saga, transaccionId);
    }


}
