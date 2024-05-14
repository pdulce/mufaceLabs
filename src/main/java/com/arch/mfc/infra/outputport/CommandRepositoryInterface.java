package com.arch.mfc.infra.outputport;

import com.arch.mfc.infra.domain.BaseEntity;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandRepositoryInterface extends JpaRepository<BaseEntity, Long> {
    public BaseEntity grabar(BaseEntity entity);

    public List<BaseEntity> buscarTodos();

    public BaseEntity buscarPorId(Long id);

    public List<BaseEntity> buscarPorCriterios(BaseEntity entity);

    public BaseEntity borrarPorId(Long id);
}
