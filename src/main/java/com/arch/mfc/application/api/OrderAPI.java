package com.arch.mfc.application.api;

import java.math.BigDecimal;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.application.service.command.CustomerOrderCommandAdapter;
import com.arch.mfc.application.service.command.CustomerCommandAdapter;
import com.arch.mfc.application.service.query.CustomerOrderQueryServiceConsumerAdapter;
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
    CustomerCommandAdapter customerCommandService;

    @Autowired
    CustomerOrderCommandAdapter customerOrderCommandService;

    @Autowired
    CustomerOrderQueryServiceConsumerAdapter customerOrderQueryServiceConsumerAdapter;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerOrder create(@RequestParam Long customerId, @RequestParam BigDecimal total) {
        try{
            Customer customer = customerCommandService.findById(customerId);
            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setCustomer(customer);
            customerOrder.setTotal(total);
            return customerOrderCommandService.insert(customerOrder);
        } catch (Throwable exc) {
            return null;
        }
    }
    
}
