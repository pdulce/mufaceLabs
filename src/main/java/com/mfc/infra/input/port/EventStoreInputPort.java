package com.mfc.infra.input.port;

import com.mfc.infra.event.Event;

import java.util.List;
import java.util.Map;

public interface EventStoreInputPort {

    void saveEvent(String almacen, String id, Event<?> eventArch);

    void update(String almacen, String id, String idEntry, Event<?> eventArch);

    List<Object> findById(String almacen, String id);

    Map<String, List<Object>> findAll(String almacen);

    void deleteAll(String almacen);

}
