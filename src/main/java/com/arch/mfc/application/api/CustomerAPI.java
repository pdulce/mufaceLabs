package com.arch.mfc.application.api;


import java.util.List;
import java.util.Map;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.service.command.CustomerCommandCommandService;
import com.arch.mfc.application.service.query.CustomerQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI {

    @Autowired
    CustomerCommandCommandService customerCommandService;
    @Autowired
    CustomerQueryService customerQueryService;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer create(@RequestParam String name, @RequestParam String country ) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setCountry(country);
        return customerCommandService.save(customer);
    }

    @PutMapping(value = "update", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer update(@RequestParam Long id, @RequestParam String country ) {
        Customer customer = customerCommandService.findById(id);
        customer.setCountry(country);
        return customerCommandService.save(customer);
    }

    @GetMapping(value = "getAllFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllFromCommandDB() {
        return customerCommandService.findAll();
    }

    @GetMapping(value = "getByNameFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllWithName(@RequestParam String name) {
        return customerCommandService.findByFieldvalue("name", name);
    }

    @GetMapping(value = "getByCountryFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllOfThisCountry(@RequestParam String country) {
        return customerCommandService.findByFieldvalue("country", country);
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
