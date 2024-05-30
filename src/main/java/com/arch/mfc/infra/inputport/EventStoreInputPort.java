package com.arch.mfc.infra.inputport;

import com.arch.mfc.infra.event.Event;

import java.util.List;
import java.util.Map;

public interface EventStoreInputPort {

    void saveEvent(Event<?> eventArch) ;
    List<Object> findById(String almacen, String id);

    Map<String, List<Object>> findAll(String almacen);

}
