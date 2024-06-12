package com.mfc.backend.microclientes.api.dto;

import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {

    private Long id;

    private String country;

    private String name;

    private List<CustomerOrder> customerOrders;

    public CustomerDTO() {}
    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.customerOrders = customer.getCustomerOrders();
        this.country = customer.getCountry();
    }

}
