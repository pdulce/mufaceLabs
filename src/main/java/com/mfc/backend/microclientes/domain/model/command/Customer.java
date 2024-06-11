package com.mfc.backend.microclientes.domain.model.command;

import com.mfc.infra.utils.ConstantMessages;
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
    @NotEmpty(message = "{" + ConstantMessages.NOT_EMPTY_LET + "}")
    private String country;

    @Column
    @NotEmpty(message = "El country no puede ser vacío")
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders;

}
