package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

public interface QueryInputPort<T> {
    
    void deleteReg( String id );
    void saveReg( T document);

    Map<String, Object> findById(String id);
    List<Map<String, Object>> findAll();



}
