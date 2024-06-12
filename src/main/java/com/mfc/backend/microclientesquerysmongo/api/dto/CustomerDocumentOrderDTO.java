package com.mfc.backend.microclientesquerysmongo.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerDocumentOrderDTO {

    private Long id;

    private CustomerDocumentOrderDTO customer;

    private BigDecimal total;

    public CustomerDocumentOrderDTO() { }

    public CustomerDocumentOrderDTO(CustomerDocumentOrderDTO customerOrder) {
        this.id = customerOrder.getId();
        this.customer = customerOrder.getCustomer();
        this.total = customerOrder.getTotal();
    }

}
