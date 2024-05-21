package com.arch.mfc.application.domain;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String country;

    @Column
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders;

    @Override
    public void setId(Long idE) {
        this.id = idE;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
