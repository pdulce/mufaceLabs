package com.mfc.backend.microclientes.api.bussinessdomain;


import java.util.ArrayList;
import java.util.List;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.backend.microclientes.domain.service.command.CustomerCommandServicePort;
import com.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI extends BaseRestController {

    @Autowired
    CustomerCommandServicePort customerCommandService;

    @Override
    @GetMapping("saludar")
    @PreAuthorize("hasRole('muface')")
    public String saludar(){
        return super.saludar();
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO update(@RequestBody @NotNull CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO);
        this.customerCommandService.update(customer);
        CustomerDTO customerDTOUpdated = new CustomerDTO(customer);
        return customerDTOUpdated;
    }

    @DeleteMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@RequestParam Long id) {
        if (id != null) {
            Customer customer = this.customerCommandService.findById(id);
            this.customerCommandService.delete(customer);
        } else {
            this.customerCommandService.deleteAll();
        }
    }

    @DeleteMapping(value = "deleteAll")
    public void deleteAll() {
        this.customerCommandService.deleteAll();
    }

    @GetMapping(value = "allCustomers", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = new ArrayList<>();
        this.customerCommandService.findAll().forEach((customer -> {
            customers.add(new CustomerDTO(customer));
        }));
        return customers;
    }

    @GetMapping(value = "allCustomersByName", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomersByName(@RequestParam String name) {
        List<CustomerDTO> customers = new ArrayList<>();
        this.customerCommandService.findAllByFieldvalue("name", name).forEach((customer -> {
            customers.add(new CustomerDTO(customer));
        }));
        return customers;
    }

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO get(@RequestParam Long customerId) {
        Customer customer = this.customerCommandService.findById(customerId);
        return new CustomerDTO(customer);
    }


    /** Invocación a un método personalizado */
    @GetMapping(value = "allCustomersByCountry", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomersByCountry(@RequestParam String country) {
        List<CustomerDTO> customers = new ArrayList<>();
        this.customerCommandService.dameListaCustomersDePaises(country).forEach((customer -> {
            customers.add(new CustomerDTO(customer));
        }));
        return customers;
    }



}
