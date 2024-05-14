package com.arch.mfc.application.api;


import java.util.List;
import java.util.Map;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.service.CustomerService;
import com.arch.mfc.infra.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arch.mfc.infra.inputport.CQRSMessageBrokerInputPort;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI {

    @Autowired
    CustomerService customerService;

    @Autowired
    CQRSMessageBrokerInputPort messageBrokerInputPort;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public BaseEntity create(@RequestParam String name, @RequestParam String country ) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setCountry(country);
        return customerService.create(customer);
    }

    @PostMapping(value = "getallWithoutCQRS", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<BaseEntity> getAllWithoutCQRS() {
        return customerService.getAll();
    }

    @PostMapping(value = "getallCustomerCQRS", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getallNonCQRS() {
        return messageBrokerInputPort.getAll( "customer" );
    }

    @PostMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public BaseEntity get( @RequestParam Long customerId ) {
        return customerService.getById(customerId);
    }


}
