package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

public interface QueryMessageBrokerInputPort {
    
    void deleteReg( String almacen, Map<String, Class<?>> reg );
    void updateReg( String almacen, Map<String, Class<?>> reg );
    void insertReg( String almacen, Map<String, Class<?>> reg );
    List<Map<String, Object>> getAll( String almacen );

}
