package com.arch.mfc.infra.events.port;

import com.arch.mfc.infra.events.EventArch;

import java.util.List;

public interface EventStoreInputPort {

    void saveEvent(EventArch<?> eventArch) ;
    EventArch<?> findById(String almacen, String id);

    List<EventArch<?>> findAll(String almacen);

}
