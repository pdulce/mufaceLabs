package com.arch.mfc.infra.outputport;

import java.util.List;
import java.util.Map;

public interface QueryRepositoryInterface {

    void save( Map<String,Object> reg, Class<?> clazz );

    void delete( String id, Class<?> clazz );

    Map<String,Object> getById( String id, Class<?> clazz );

    List<Map<String,Object>> getAll( Class<?> clazz );
}
