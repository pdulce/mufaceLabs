package com.mfc.infra.input.port;

import com.mfc.infra.event.Event;

import java.util.List;
import java.util.Map;

public interface EventStoreInputPort {

    void saveEvent(String type, String applicationId, String almacen, String id, Event<?> eventArch);

    void update(String type, String applicationId, String almacen, String id, String idEntry, Event<?> eventArch);

    List<Object> findAllByAppAndStoreAndAggregatedId(String type, String applicationid, String almacen, String id);

    Map<String, List<Object>> findAllByAppAndStore(String type, String applicationid, String almacen);

    List<Map<String, List<Object>>> findAllByApp(String type, String applicationId);

}
