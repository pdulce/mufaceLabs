package com.arch.mfc.infra.inputadapter.http;

import java.math.BigDecimal;

import com.arch.mfc.domain.Customer;
import com.arch.mfc.domain.Order;
import com.arch.mfc.infra.inputport.CustomerInputPort;
import com.arch.mfc.infra.outputadapter.relational.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arch.mfc.infra.inputport.OrderInputPort;

@RestController
@RequestMapping(value = "order")
public class OrderAPI {

    @Autowired
    CustomerInputPort customerInputPort;
    @Autowired
    OrderInputPort orderInputPort;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public Order create(@RequestParam Long customerId, @RequestParam BigDecimal total ) {
        Customer customer = customerInputPort.getById(customerId);
        return orderInputPort.createOrder(customer, total);
   }
    
}
