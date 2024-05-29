package com.arch.mfc.infra.inputport;

import com.arch.mfc.infra.events.Event;

import java.util.List;
import java.util.stream.Collectors;

public interface EventStoreInputPort {

    public void saveEvent(Event<?> event) ;
    public Event<?> getById(String id);

    public List<Event<?>> getAll() ;

}
