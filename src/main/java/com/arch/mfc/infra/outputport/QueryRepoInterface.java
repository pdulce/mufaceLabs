package com.arch.mfc.infra.outputport;

import java.util.List;
import java.util.Map;

public interface QueryRepoInterface <T> {

    void save(Map<String,Object> reg, Class<T> clazz ) throws InstantiationException, IllegalAccessException;

    void delete( String id, Class<T> clazz );

    Map<String,Object> getById( String id, Class<T> clazz );

    List<Map<String,Object>> getAll(Class<T> clazz );
}
