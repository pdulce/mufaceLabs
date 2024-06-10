package com.mfc.backend.microclientes.api.bussinessdomain;


import java.util.List;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.infra.controller.BaseRestController;
import com.mfc.infra.output.port.CommandPort;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI extends BaseRestController {

    @Autowired
    CommandPort<Customer> customerCommandService;

    @Override
    @GetMapping("saludar")
    public String saludar(){
        return super.saludar();
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer update(@RequestBody @NotNull Customer customer) {
        return this.customerCommandService.update(customer);
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
    public List<Customer> getAllCustomers() {
        return this.customerCommandService.findAll();
    }

    @GetMapping(value = "allCustomersByName", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllCustomersByName(@RequestParam String name) {
        return this.customerCommandService.findAllByFieldvalue("name", name);
    }

    @GetMapping(value = "allCustomersByCountry", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllCustomersByCountry(@RequestParam String country) {
        return this.customerCommandService.findAllByFieldvalue("country", country);
    }

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer get(@RequestParam Long customerId) {
        return this.customerCommandService.findById(customerId);
    }


}
