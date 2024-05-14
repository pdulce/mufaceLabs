package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.Entity;


public interface GenericInputPort {

    public BaseEntity create(Map<String, Object> mapParams);

    public BaseEntity update(BaseEntity customer);

    public BaseEntity getById(Long id);

    public List<BaseEntity> getAll();

}
