package com.arch.mfc.application.rest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.service.OrderService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "order")
public class OrderAPI {

    /*@Autowired
    @Qualifier("CustomerService")
    GenericInputPort customerInputPort;*/

    @Autowired
    //@Qualifier("com.arch.mfc.application.service.OrderService")
    //GenericInputPort orderInputPort;
    OrderService orderInputPort;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public Entity create(@RequestParam Long customerId, @RequestParam BigDecimal total) {
        Customer customer = null; // (Customer) customerInputPort.getById(customerId);
        Map<String, Object> mapObj = new HashMap<>();
        mapObj.put("customer", customer);
        mapObj.put("total", total);
        return orderInputPort.create(mapObj);
   }
    
}
