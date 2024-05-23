package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

public interface EventMessageBrokerInputPort {
    
    void insertEvent( String almacen, Map<String, Object> reg );
    List<Map<String, Object>> getAll( String almacen );

}
