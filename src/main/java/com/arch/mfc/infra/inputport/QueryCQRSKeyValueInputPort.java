package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

public interface QueryCQRSKeyValueInputPort {
    
    void deleteReg( String almacen, Map<String, Object> reg );
    void updateReg( String almacen, Map<String, Object> reg );
    void insertReg( String almacen, Map<String, Object> reg );
    List<Map<String, Object>> findAll(String almacen );

}
