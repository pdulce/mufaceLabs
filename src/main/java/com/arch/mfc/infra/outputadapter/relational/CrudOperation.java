package com.arch.mfc.infra.outputadapter.relational;

import com.arch.mfc.infra.domain.BaseEntity;
import com.arch.mfc.infra.inputport.GenericInputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public abstract class CrudOperation implements GenericInputPort {

    public abstract JpaRepository<BaseEntity, Long> getJPaRepository();

    @Override
    public BaseEntity create(BaseEntity baseEntity) {
        baseEntity.setId(UUID.randomUUID().getMostSignificantBits());
        return getJPaRepository().save(baseEntity);
    }

    @Override
    public BaseEntity delete(BaseEntity baseEntity) {
        return getJPaRepository().save(baseEntity);
    }

    @Override
    public BaseEntity update(BaseEntity baseEntity) {
        return getJPaRepository().save(baseEntity);
    }

    @Override
    public BaseEntity getById(Long id) {
        return getJPaRepository().findById(id).get();
    }

    @Override
    public List<BaseEntity> getAll() {
        return getJPaRepository().findAll();
    }

    @Override
    public Page<BaseEntity> getAllPaged(Pageable pageable) { return getJPaRepository().findAll(pageable); }


}
