package com.mfc.infra.output.port;

import com.mfc.infra.exceptions.NotExistException;

import java.util.List;


public interface CommandPort<T> {

    T insert(T entity) throws NoSuchFieldException;

    T update(T entity) throws NotExistException;

    void delete(T entity) throws NotExistException;

    void deleteAllList(List<T> entities) throws NotExistException;
    void deleteAll();

    T findById(Long id) throws NotExistException;

    List<T> findAll();

    List<T> findAllByFieldvalue(String fieldName, Object fieldValue);

    String getDocumentEntityClassname();

}
