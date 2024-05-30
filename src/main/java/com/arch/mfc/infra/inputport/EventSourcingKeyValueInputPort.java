package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

public interface EventSourcingKeyValueInputPort {

    void saveEvent( String almacen, Map<String, Object> reg );
    List<Map<String, Object>> findAll(String almacen );

}
