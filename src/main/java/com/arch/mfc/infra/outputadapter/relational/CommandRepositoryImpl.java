package com.arch.mfc.infra.outputadapter.relational;

import com.arch.mfc.infra.outputadapter.relational.inner.CommandRepositoryAbstractImpl;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandRepositoryImpl extends CommandRepositoryAbstractImpl {

    /*** architecture adhoc methods ***/

    public Entity grabar(Entity entity) {
        return save(entity);
    }

    public List<Entity> buscarTodos() {
        return findAll();
    }

    public Entity buscarPorId(Long id) {
        return findById(id).get();
    }

    public List<Entity> buscarPorCriterios(Entity entity) {
        Example<Entity> criteria = new Example<Entity>() {
            @Override
            public Entity getProbe() {
                return this.getProbe();
            }

            @Override
            public ExampleMatcher getMatcher() {
                return this.getMatcher();
            }

            @Override
            public Class<Entity> getProbeType() {
                return Example.super.getProbeType();
            }
        };
        return findAll(criteria);
    }

    public Entity borrarPorId(Long id) {
        Entity entity = getById(id);
        if (entity != null) {
            deleteById(id);
            return entity;
        }
        return null;
    }
}
