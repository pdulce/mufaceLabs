package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.formula.functions.T;


public interface GenericInputPort<T> {

    public T save(T entity) throws NoSuchFieldException;

    public T update(T entity);

    public void delete(T entity);

    public T findById(Long id);

    public List<T> findAll();

}
