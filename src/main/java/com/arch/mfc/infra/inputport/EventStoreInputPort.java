package com.arch.mfc.infra.inputport;

import com.arch.mfc.infra.events.EventArch;

import java.util.List;

public interface EventStoreInputPort {

    public void saveEvent(EventArch<?> eventArch) ;
    public EventArch<?> getById(String almacen, String id);

    public List<EventArch<?>> getAll(String almacen);

}
