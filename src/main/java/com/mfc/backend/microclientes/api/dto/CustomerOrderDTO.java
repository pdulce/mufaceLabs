package com.mfc.backend.microclientes.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerOrderDTO {

    private Long id;

    private Long customerId;

    private String customerName;

    private BigDecimal total;

    public CustomerOrderDTO() { }

    public CustomerOrderDTO(Long id, Long customerId, String customerName, BigDecimal total) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.total = total;
    }

}
