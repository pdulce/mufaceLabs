package com.arch.mfc.application.api;

import java.math.BigDecimal;
import java.util.UUID;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.application.service.CustomerOrderService;
import com.arch.mfc.application.service.CustomerService;
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

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerOrderService customerOrderService;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public Entity create(@RequestParam Long customerId, @RequestParam BigDecimal total) {
        Customer customer = (Customer) customerService.getById(customerId);
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(UUID.randomUUID().timestamp());
        customerOrder.setCustomer(customer);
        customerOrder.setTotal(total);
        return customerOrderService.create(customerOrder);
   }
    
}