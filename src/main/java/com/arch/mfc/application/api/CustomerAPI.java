package com.arch.mfc.application.api;


import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.arch.mfc.application.domain.command.Customer;
import com.arch.mfc.application.service.CustomerService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.arch.mfc.infra.inputport.CQRSMessageBrokerInputPort;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI {

    @Autowired
    CustomerService customerService;

    @Autowired
    CQRSMessageBrokerInputPort messageBrokerInputPort;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer create(@RequestParam String name, @RequestParam String country ) {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().getMostSignificantBits());
        customer.setName(name);
        customer.setCountry(country);
        return customerService.save(customer);
    }

    @GetMapping(value = "getallWithoutCQRS", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllWithoutCQRS() {
        return customerService.findAll();
    }

    @GetMapping(value = "getallCustomerCQRS", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getallNonCQRS() {
        return messageBrokerInputPort.getAll( "customer" );
    }

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer get( @RequestParam Long customerId ) {
        return customerService.findById(customerId);
    }


}
