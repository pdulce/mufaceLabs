package com.mfc.backend.microclientes.api.dto;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerOrderDTO {

    private Long id;

    private Customer customer;

    private BigDecimal total;

    public CustomerOrderDTO() { }

    public CustomerOrderDTO(CustomerOrder customerOrder) {
        this.id = customerOrder.getId();
        this.customer = customerOrder.getCustomer();
        this.total = customerOrder.getTotal();
    }

}
