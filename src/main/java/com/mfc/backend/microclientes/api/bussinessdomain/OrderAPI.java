package com.mfc.backend.microclientes.api.bussinessdomain;

import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import com.mfc.backend.microclientes.domain.service.command.CustomerOrderCommandAdapter;
import com.mfc.infra.controller.BaseRestController;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "order")
public class OrderAPI extends BaseRestController {

    @Autowired
    CustomerOrderCommandAdapter customerOrderCommandService;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerOrder create(@PathVariable @NotNull CustomerOrder customerOrder) {
        return customerOrderCommandService.insert(customerOrder);
    }
    
}
