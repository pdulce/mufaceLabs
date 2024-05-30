package com.arch.mfc.infra.inputport;

import com.arch.mfc.infra.event.Event;

import java.util.List;
import java.util.Map;

public interface EventStoreInputPort {

    void saveEvent(Event<?> eventArch) ;
    Event<?> findById(String almacen, String id);

    Map<String, Event<?>> findAll(String almacen);

}
