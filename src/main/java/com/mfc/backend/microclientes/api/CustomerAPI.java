package com.mfc.backend.microclientes.api;


import java.util.List;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.api.usecases.BorrarCustomerUseCase;
import com.mfc.backend.microclientes.api.usecases.ConsultasCustomerUseCase;
import com.mfc.infra.controller.ArqBaseRestController;
import com.mfc.backend.microclientes.api.usecases.ActualizarCustomerUseCase;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI extends ArqBaseRestController {

    @Autowired
    ActualizarCustomerUseCase actualizarCustomerUseCase;

    @Autowired
    BorrarCustomerUseCase borrarCustomerUseCase;

    @Autowired
    ConsultasCustomerUseCase consultasCustomerUseCase;

    @Override
    @GetMapping("saludar")
    @PreAuthorize("hasRole('muface')")
    public String saludar(){
        return super.saludar();
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO update(@RequestBody @NotNull CustomerDTO customerDTO) {
        return actualizarCustomerUseCase.ejecutar(customerDTO);
    }

    @DeleteMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@RequestParam Long id) {
        if (id != null) {
            CustomerDTO customer = this.consultasCustomerUseCase.ejecutar(id);
            this.borrarCustomerUseCase.ejecutar(customer);
        } else {
            this.borrarCustomerUseCase.ejecutar();
        }
    }

    @DeleteMapping(value = "deleteAll")
    public void deleteAll() {
        this.borrarCustomerUseCase.ejecutar();
    }

    @GetMapping(value = "allCustomers", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers() {
        return this.consultasCustomerUseCase.ejecutar();
    }

    @GetMapping(value = "allCustomersByName", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomersByName(@RequestParam String name) {
        return this.consultasCustomerUseCase.ejecutar(name);
    }

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO get(@RequestParam Long customerId) {
        return this.consultasCustomerUseCase.ejecutar(customerId);
    }

    /** Invocación a un método personalizado */
    @GetMapping(value = "allCustomersByCountry", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomersByCountry(@RequestParam String country) {
        return this.consultasCustomerUseCase.dameListaCustomersDePaises(country);
    }

}
