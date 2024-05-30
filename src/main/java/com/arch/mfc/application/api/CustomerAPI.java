package com.arch.mfc.application.api;


import java.util.List;
import java.util.Map;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.service.command.CustomerCommandAdapter;
import com.arch.mfc.application.service.query.CustomerQueryServiceConsumerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI {

    @Autowired
    CustomerCommandAdapter customerCommandService;
    @Autowired
    CustomerQueryServiceConsumerAdapter customerQueryService;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer create(@RequestParam String name, @RequestParam String country ) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setCountry(country);
        return customerCommandService.insert(customer);
    }

    @PutMapping(value = "update", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer update(@RequestParam Long id, @RequestParam String country ) {
        Customer customer = customerCommandService.findById(id);
        customer.setCountry(country);
        return customerCommandService.update(customer);
    }

    @DeleteMapping(value = "delete", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteByname(@RequestParam String name) {
        List<Customer> customers = this.customerCommandService.findAllByFieldvalue("name", name);
        customers.forEach((customer) -> {
            customerCommandService.delete(customer);
        });
    }

    @DeleteMapping(value = "deleteAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAll() {
        this.customerCommandService.deleteAll();
    }

    @GetMapping(value = "getAllFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllFromCommandDB() {
        return customerCommandService.findAll();
    }

    @GetMapping(value = "getByNameFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllWithName(@RequestParam String name) {
        return customerCommandService.findAllByFieldvalue("name", name);
    }

    @GetMapping(value = "getByCountryFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllOfThisCountry(@RequestParam String country) {
        return customerCommandService.findAllByFieldvalue("country", country);
    }

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer get(@RequestParam Long customerId ) {
        return customerCommandService.findById(customerId);
    }


    @GetMapping(value = "getAllFromQueryMongoDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getAllFromQueryMongoDB() {
        return customerQueryService.findAll();
    }


}
