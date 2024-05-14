package com.arch.mfc.infra.outputport;

import java.util.List;
import java.util.Map;

public interface QueryRepositoryInterface {

    public void save( Map<String,Object> reg, Class<?> clazz );

    public void delete( String id, Class<?> clazz );

    public Map<String,Object> getById( String id, Class<?> clazz );

    public List<Map<String,Object>> getAll( Class<?> clazz );    
}
