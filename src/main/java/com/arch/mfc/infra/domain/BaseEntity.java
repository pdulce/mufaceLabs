package com.arch.mfc.infra.domain;

import com.arch.mfc.application.domain.CustomerOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;


@Entity
@Getter
@Setter
public abstract class BaseEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Override
    public String name() {
        return "BaseEntity";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return BaseEntity.class;
    }


}
