package com.mfc.backend.microclientes.api;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.api.usecases.CrearPedidoUseCase;
import com.mfc.infra.controller.ArqBaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "order")
public class OrderAPI extends ArqBaseRestController {

    @Autowired
    CrearPedidoUseCase crearPedidoUseCase;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerOrderDTO create(@PathVariable @NotNull CustomerOrderDTO customerOrderDTO) {
        return this.crearPedidoUseCase.ejecutar(customerOrderDTO);
    }
    
}
