package com.mfc.infra.input.port;

import com.mfc.infra.event.Event;

import java.util.List;
import java.util.Map;

public interface EventStoreInputPort {

    void saveEvent(String applicationId, String almacen, String id, Event<?> eventArch);

    void update(String applicationId, String almacen, String id, String idEntry, Event<?> eventArch);

    List<Object> findAllByAppAndStoreAndAggregatedId(String applicationid, String almacen, String id);

    Map<String, List<Object>> findAllByAppAndStore(String applicationid, String almacen);

    List<Map<String, List<Object>>> findAllByApp(String applicationId);

}
