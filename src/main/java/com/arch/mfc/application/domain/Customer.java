package com.arch.mfc.application.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @NotNull(message = "La clave no puede ser nula")
    private Long id;

    @Column
    private String country;

    @Column
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders;

}
