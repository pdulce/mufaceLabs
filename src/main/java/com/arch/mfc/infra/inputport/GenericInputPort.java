package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

import jakarta.persistence.Entity;
import org.apache.poi.ss.formula.functions.T;

public interface GenericInputPort {

    public Entity create(Map<String, Object> mapParams);

    public Entity update(Entity customer);

    public Entity getById(Long id);

    public List<Entity> getAll();

}
