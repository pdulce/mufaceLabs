package com.arch.mfc.infra.inputport;

import java.util.List;


public interface GenericInputPort<T> {

    public T save(T entity) throws NoSuchFieldException;

    public T update(T entity);

    public void delete(T entity);

    public T findById(Long id);

    public List<T> findAll();

}
