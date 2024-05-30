package com.arch.mfc.infra.outputport;

import com.arch.mfc.infra.events.EventArch;
import com.arch.mfc.infra.utils.ConversionUtils;

import java.util.List;


public interface GenericCommandPort<T> {

    T insert(T entity) throws NoSuchFieldException;

    T update(T entity);

    void delete(T entity);

    void deleteAll();

    public T findById(Long id);

    public List<T> findAll();

}
