package com.mfc.infra.output.port;

import com.mfc.infra.exceptions.NotExistException;

import java.util.List;


public interface CommandPort<T> {

    T insert(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteAllList(List<T> entities);
    void deleteAll();

    T findById(Long id);

    List<T> findAll();

    List<T> findAllByFieldvalue(String fieldName, Object fieldValue);

    String getDocumentEntityClassname();

}
