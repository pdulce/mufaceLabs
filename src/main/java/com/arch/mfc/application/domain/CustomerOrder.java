package com.arch.mfc.application.domain;

import java.math.BigDecimal;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomerOrder extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private BigDecimal total;

}
