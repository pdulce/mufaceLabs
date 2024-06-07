package com.mfc.backend.microcustomers.api;


import java.util.List;
import java.util.Map;

import com.mfc.backend.microcustomers.domain.model.command.Customer;
import com.mfc.backend.microcustomers.domain.service.command.CustomerCommandAdapter;
import com.mfc.backend.microcustomers.domain.service.query.CustomerQueryServiceConsumerAdapter;
import com.mfc.infra.controller.BaseRestController;
import com.mfc.infra.input.adapter.EventStoreConsumerAdapter;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI extends BaseRestController {

    @Autowired
    CustomerCommandAdapter customerCommandService;

    @Autowired
    CustomerQueryServiceConsumerAdapter customerQueryService;

    @Override
    @GetMapping("saludar")
    public String saludar(){
        return super.saludar();
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer update(@RequestBody @NotNull Customer customer) {
        try{
            return customerCommandService.update(customer);
        } catch (Throwable exc) {
            return null;
        }
    }

    @DeleteMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteByname(@RequestParam String name) {
        if (name != null) {
            List<Customer> customers = this.customerCommandService.findAllByFieldvalue("name", name);
            customers.forEach((customer) -> {
                try {
                    this.customerCommandService.delete(customer);
                } catch (Throwable exc) {
                    return;
                }
            });
        } else {
            this.customerCommandService.deleteAll();
        }
    }

    @DeleteMapping(value = "deleteAll")
    public void deleteAll() {
        this.customerCommandService.deleteAll();
    }

    @GetMapping(value = "getAllFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllFromCommandDB() {
        return this.customerCommandService.findAll();
    }

    @GetMapping(value = "getByNameFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllWithName(@RequestParam String name) {
        return this.customerCommandService.findAllByFieldvalue("name", name);
    }

    @GetMapping(value = "getByCountryFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllOfThisCountry(@RequestParam String country) {
        return this.customerCommandService.findAllByFieldvalue("country", country);
    }

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer get(@RequestParam Long customerId) {
        try{
            return this.customerCommandService.findById(customerId);
        } catch (Throwable exc) {
            return null;
        }
    }

    /*** CONSULTAS CONTRA EL DOMINIO DE QUERIES ***/

    @GetMapping(value = "getFromQueryStoreMongoById", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getFromQueryStoreMongoById(@RequestParam String customerId) {
        return this.customerQueryService.findById(customerId);
    }

    @GetMapping(value = "getAllFromQueryStoreMongo", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getAllFromQueryStoreMongo() {
        return this.customerQueryService.findAll();
    }




}
