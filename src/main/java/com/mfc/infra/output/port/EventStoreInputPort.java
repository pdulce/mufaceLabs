package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

import java.util.List;
import java.util.Map;

public interface EventStoreInputPort {

    void saveEvent(String type, String applicationId, String almacen, String id, Event<?> eventArch);

    void update(String type, String applicationId, String almacen, String id, String idEntry, Event<?> eventArch);

    List<Event<?>> findAllByAppAndStoreAndAggregatedId(String type, String applicationid, String almacen, String id);

    List<Event<?>> findAllByAppAndStore(String type, String applicationid, String almacen);

    List<Event<?>> findAllByApp(String type, String applicationId);
    
    List<Event<?>> findAllStoreType(String type);

}
