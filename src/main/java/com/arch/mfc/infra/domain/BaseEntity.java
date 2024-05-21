package com.arch.mfc.infra.domain;

import lombok.Data;

import java.lang.annotation.Annotation;

@Data
public abstract class BaseEntity implements IEntity {

    @Override
    public String name() {
        return "BaseEntity";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return BaseEntity.class;
    }


}
