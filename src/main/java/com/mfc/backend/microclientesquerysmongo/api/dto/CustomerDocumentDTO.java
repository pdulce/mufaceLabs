package com.mfc.backend.microclientesquerysmongo.api.dto;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerOrderDocument;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDocumentDTO {

    private String id;

    private String country;

    private String name;

    private List<CustomerOrderDocument> customerOrders;

    public CustomerDocumentDTO() {}
    public CustomerDocumentDTO(CustomerDocument customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.customerOrders = customer.getCustomerOrders();
        this.country = customer.getCountry();
    }

}
