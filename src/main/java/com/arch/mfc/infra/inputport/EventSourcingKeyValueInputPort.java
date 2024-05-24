package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

public interface EventSourcingKeyValueInputPort {

    void insertEvent( String almacen, Map<String, Object> reg );
    List<Map<String, Object>> getAll( String almacen );

}
