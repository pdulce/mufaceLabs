package com.mfc.backend.microclientes.domain.model;

import java.math.BigDecimal;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private BigDecimal total;

    public CustomerOrder() {}

    public CustomerOrder(CustomerOrderDTO customerOrderDTO) {
        this.id = customerOrderDTO.getId();
        Customer customer = new Customer();
        customer.setId(customerOrderDTO.getCustomerId());
        this.customer = customer;
        this.total = customerOrderDTO.getTotal();
    }

}
