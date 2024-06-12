package com.mfc.backend.microclientes.api.bussinessdomain;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import com.mfc.infra.controller.BaseRestController;
import com.mfc.infra.output.port.CommandServicePort;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "order")
public class OrderAPI extends BaseRestController {

    @Autowired
    CommandServicePort<CustomerOrder, Long> customerOrderCommandService;

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerOrderDTO create(@PathVariable @NotNull CustomerOrderDTO customerOrderDTO) {
        CustomerOrder customerOrder = new CustomerOrder(customerOrderDTO);
        CustomerOrderDTO customerOrderDtoCreated =
                new CustomerOrderDTO(this.customerOrderCommandService.insert(customerOrder));
        return customerOrderDtoCreated;
    }
    
}
