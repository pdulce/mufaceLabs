package com.mfc.backend.microclientes.domain.model.command;

import java.math.BigDecimal;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
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
        this.customer = customerOrderDTO.getCustomer();
        this.total = customerOrderDTO.getTotal();
    }

}
