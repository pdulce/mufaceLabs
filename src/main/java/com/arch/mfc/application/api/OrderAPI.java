package com.arch.mfc.application.api;

import java.math.BigDecimal;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.application.service.command.CustomerOrderCommandCommandService;
import com.arch.mfc.application.service.command.CustomerCommandCommandService;
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
    CustomerCommandCommandService customerCommandService;

    @Autowired
    CustomerOrderCommandCommandService customerOrderCommandService;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerOrder create(@RequestParam Long customerId, @RequestParam BigDecimal total) {
        Customer customer = customerCommandService.findById(customerId);
        CustomerOrder customerOrder = new CustomerOrder();
        //customerOrder.setId(UUID.randomUUID().timestamp()); // NO LO NECESITAMOS YA QUE ESTA COMO AUTOINCREMENTAL
        customerOrder.setCustomer(customer);
        customerOrder.setTotal(total);
        return customerOrderCommandService.save(customerOrder);
   }
    
}
