package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

import com.arch.mfc.infra.domain.BaseEntity;
import com.arch.mfc.infra.domain.IEntity;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GenericInputPort {

    IEntity create(IEntity baseEntity);

    IEntity update(IEntity baseEntity);

    IEntity delete(IEntity baseEntity);

    IEntity getById(Long id);

    List<IEntity> getAll();

    Page<IEntity> getAllPaged(Pageable pageable);

}
