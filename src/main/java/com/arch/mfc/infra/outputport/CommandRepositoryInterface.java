package com.arch.mfc.infra.outputport;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandRepositoryInterface extends JpaRepository<Entity, Long> {
    public Entity grabar(Entity entity);

    public List<Entity> buscarTodos();

    public Entity buscarPorId(Long id);

    public List<Entity> buscarPorCriterios(Entity entity);

    public Entity borrarPorId(Long id);
}
