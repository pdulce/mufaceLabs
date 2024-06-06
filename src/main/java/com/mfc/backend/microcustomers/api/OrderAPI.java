package com.arch.mfc.backend.micro.customers.api;

import com.arch.mfc.backend.micro.customers.domain.service.command.CustomerOrderCommandAdapter;
import com.arch.mfc.backend.micro.customers.domain.model.command.CustomerOrder;
import com.arch.mfc.backend.micro.customers.domain.service.command.CustomerCommandAdapter;
import com.arch.mfc.backend.micro.customers.domain.service.query.CustomerOrderQueryServiceConsumerAdapter;
import com.arch.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "order")
public class OrderAPI extends BaseRestController {

    @Autowired
    CustomerCommandAdapter customerCommandService;

    @Autowired
    CustomerOrderCommandAdapter customerOrderCommandService;

    @Autowired
    CustomerOrderQueryServiceConsumerAdapter customerOrderQueryServiceConsumerAdapter;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerOrder create(@PathVariable @NotNull CustomerOrder customerOrder) {
        try{
            customerOrder.setId(UUID.randomUUID().getMostSignificantBits());
            return customerOrderCommandService.insert(customerOrder);
        } catch (Throwable exc) {
            return null;
        }
    }
    
}
