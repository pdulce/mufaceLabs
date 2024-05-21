package com.arch.mfc.infra.domain;

import jakarta.persistence.*;
import lombok.Data;


import java.lang.annotation.Annotation;


@Entity
@Data
public abstract class BaseEntity implements IEntity {

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
