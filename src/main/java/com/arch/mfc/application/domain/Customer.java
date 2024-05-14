package com.arch.mfc.application.domain;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity {

    @Column
    private String country;

    @Column
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders;


}
