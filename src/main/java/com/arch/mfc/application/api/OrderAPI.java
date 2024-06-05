package com.arch.mfc.application.api;

import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.application.dataaccess.command.CustomerOrderCommandAdapter;
import com.arch.mfc.application.dataaccess.command.CustomerCommandAdapter;
import com.arch.mfc.application.dataaccess.query.CustomerOrderQueryServiceConsumerAdapter;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public CustomerOrder create(@PathVariable @NotNull CustomerOrder customerOrder) {
        try{
            customerOrder.setId(UUID.randomUUID().getMostSignificantBits());
            return customerOrderCommandService.insert(customerOrder);
        } catch (Throwable exc) {
            return null;
        }
    }
    
}
