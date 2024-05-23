package com.arch.mfc.infra.outputport;

import java.util.List;


public interface GenericCommandPort<T> {

    public T save(T entity) throws NoSuchFieldException;

    public T update(T entity);

    public void delete(T entity);

    public T findById(Long id);

    public List<T> findAll();

}
