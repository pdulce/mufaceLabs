package com.arch.mfc.application.api;


import java.util.List;
import java.util.Map;

import com.arch.mfc.application.domain.command.Customer;
import com.arch.mfc.application.domain.query.MongoCustomer;
import com.arch.mfc.application.service.commands.CustomerCommandCommandService;
import com.arch.mfc.application.service.mongo.CustomerMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.arch.mfc.infra.inputport.CQRSMessageBrokerInputPort;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI {

    @Autowired
    CustomerCommandCommandService customerCommandService;
    @Autowired
    CQRSMessageBrokerInputPort messageBrokerInputPort;

    @Autowired
    CustomerMongoService customerMongoService;


    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer create(@RequestParam String name, @RequestParam String country ) {
        Customer customer = new Customer();
        //customer.setId(UUID.randomUUID().getMostSignificantBits()); // NO LO NECESITAMOS YA QUE ESTA COMO AUTOINCR
        customer.setName(name);
        customer.setCountry(country);
        return customerCommandService.save(customer);
    }

    @GetMapping(value = "getAllFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllFromCommandDB() {
        return customerCommandService.findAll();
    }

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer get( @RequestParam Long customerId ) {
        return customerCommandService.findById(customerId);
    }


    @GetMapping(value = "getAllFromQueryRedirisDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getAllFromQueryRedirisDB() {
        return messageBrokerInputPort.
                getAll( "Customer" );
    }

    @GetMapping(value = "getAllFromQueryMongoDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getAllFromQueryMongoDB() {
        return customerMongoService.getAll( MongoCustomer.class );
    }


}
