package com.arch.mfc.application.http;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.arch.mfc.application.OrderService;
import com.arch.mfc.application.domain.Customer;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arch.mfc.infra.inputport.GenericInputPort;

@RestController
@RequestMapping(value = "order")
public class OrderAPI {

    @Autowired
    @Qualifier("CustomerService")
    GenericInputPort customerInputPort;

    @Autowired
    @Qualifier("OrderService")
    GenericInputPort orderInputPort;

    @Autowired
    private OrderService customerRepository;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public Entity create(@RequestParam Long customerId, @RequestParam BigDecimal total) {
        Customer customer = (Customer) customerInputPort.getById(customerId);
        Map<String, Object> mapObj = new HashMap<>();
        mapObj.put("customer", customer);
        mapObj.put("total", total);
        return orderInputPort.create(mapObj);
   }
    
}
