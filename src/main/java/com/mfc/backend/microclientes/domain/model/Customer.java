package com.mfc.backend.microclientes.domain.model;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.infra.utils.ArqConstantMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @NotEmpty(message = "{" + ArqConstantMessages.NOT_EMPTY_LET + "}")
    private String country;

    @Column
    @NotEmpty(message = "El country no puede ser vacío")
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders;

    public Customer() {

    }
    public Customer(CustomerDTO customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.customerOrders = null;
        this.country = customer.getCountry();
    }

}
