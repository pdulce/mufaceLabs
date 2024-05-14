package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GenericInputPort {

    BaseEntity create(BaseEntity baseEntity);

    BaseEntity update(BaseEntity baseEntity);

    BaseEntity delete(BaseEntity baseEntity);

    BaseEntity getById(Long id);

    List<BaseEntity> getAll();

    Page<BaseEntity> getAllPaged(Pageable pageable);

}
