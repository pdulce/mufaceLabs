package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.Entity;


public interface GenericInputPort {

    public BaseEntity create(BaseEntity baseEntity);

    public BaseEntity update(BaseEntity baseEntity);

    public BaseEntity delete(BaseEntity baseEntity);

    public BaseEntity getById(Long id);

    public List<BaseEntity> getAll();

}
