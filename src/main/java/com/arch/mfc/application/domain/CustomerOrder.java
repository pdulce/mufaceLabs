package com.arch.mfc.application.domain;

import java.math.BigDecimal;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CustomerOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private BigDecimal total;

    @Override
    public void setId(Long idE) {
        this.id = idE;
    }

    @Override
    public Long getId() {
        return this.id;
    }

}
