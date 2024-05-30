package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

public interface QueryCQRSDocumentInputPort<T> {
    
    void deleteReg( String id );
    void updateReg( Map<String, Object> reg, Class<T> clazz );
    void insertReg( Map<String, Object> reg, Class<T> clazz );

    Map<String, Object> findById(String id);
    List<Map<String, Object>> findAll();



}
