package com.arch.mfc.infra.outputport;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public interface QueryRepositoryInterface<T> {

    void save( Map<String,Object> reg, Class<T> clazz );

    void delete( String id, Class<T> clazz );

    Map<String,Object> getById( String id, Class<T> clazz );

    List<Map<String,Object>> getAll( Class<T> clazz );
}
