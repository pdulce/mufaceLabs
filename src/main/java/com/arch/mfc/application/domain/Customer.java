package com.arch.mfc.application.domain;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Customer extends BaseEntity {

    @Column
    private String country;

    @Column
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders;


}
