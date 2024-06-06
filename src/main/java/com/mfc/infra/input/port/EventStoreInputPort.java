package com.mfc.infra.input.port;

import java.util.List;
import java.util.Map;

public interface EventStoreInputPort {

    List<Object> findById(String almacen, String id);

    Map<String, List<Object>> findAll(String almacen);

}
