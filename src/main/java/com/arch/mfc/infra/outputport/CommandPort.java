package com.arch.mfc.infra.outputport;

import java.util.List;


public interface CommandPort<T> {

    T insert(T entity) throws NoSuchFieldException;

    T update(T entity);

    void delete(T entity);

    void deleteAll();

    T findById(Long id);

    List<T> findAll();

    String getDocumentEntityClassname();

}
