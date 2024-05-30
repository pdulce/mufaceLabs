package com.arch.mfc.infra.events.port;

import com.arch.mfc.infra.events.EventArch;

import java.util.List;

public interface EventStoreInputPort {

    public void saveEvent(EventArch<?> eventArch) ;
    public EventArch<?> findById(String almacen, String id);

    public List<EventArch<?>> findAll(String almacen);

}
