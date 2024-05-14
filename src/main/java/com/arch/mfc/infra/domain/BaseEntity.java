package com.arch.mfc.infra.domain;

import jakarta.persistence.*;

import java.lang.annotation.Annotation;

@Entity
public class BaseEntity implements Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String name() {
        return "unnamed";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return BaseEntity.class;
    }
}
