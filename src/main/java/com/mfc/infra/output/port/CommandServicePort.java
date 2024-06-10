package com.mfc.infra.output.port;

import java.util.List;


public interface CommandServicePort<T, ID> {

    T insert(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteAllList(List<T> entities);
    void deleteAll();

    T findById(ID id);

    List<T> findAll();

    List<T> findAllByFieldvalue(String fieldName, Object fieldValue);

    String getDocumentEntityClassname();

}
