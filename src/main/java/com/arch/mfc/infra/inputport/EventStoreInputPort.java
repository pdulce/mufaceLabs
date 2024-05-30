package com.arch.mfc.infra.inputport;

import com.arch.mfc.infra.event.Event;

import java.util.List;

public interface EventStoreInputPort {

    void saveEvent(Event<?> eventArch) ;
    Event<?> findById(String almacen, String id);

    List<Event<?>> findAll(String almacen);

}
