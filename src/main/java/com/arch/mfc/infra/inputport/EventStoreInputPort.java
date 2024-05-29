package com.arch.mfc.infra.inputport;

import com.arch.mfc.infra.events.adapter.Event;

import java.util.List;

public interface EventStoreInputPort {

    public void saveEvent(Event<?> event) ;
    public Event<?> getById(String id);

    public List<Event<?>> getAll() ;

}
