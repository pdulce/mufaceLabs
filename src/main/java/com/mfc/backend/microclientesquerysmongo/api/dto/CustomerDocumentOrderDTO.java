package com.mfc.backend.microclientesquerysmongo.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerDocumentOrderDTO {

    private String id;

    private Long customerId;

    private String customerName;

    private BigDecimal total;

    public CustomerDocumentOrderDTO() { }

    public CustomerDocumentOrderDTO(String id, Long customerId, String customerName, BigDecimal total) {
        this.id = id;
        this.customerId = customerId;
        this.customerName =  customerName;
        this.total = total;
    }

}
