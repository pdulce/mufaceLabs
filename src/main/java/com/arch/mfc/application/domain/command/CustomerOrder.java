package com.arch.mfc.application.domain.command;

import java.math.BigDecimal;

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

}
