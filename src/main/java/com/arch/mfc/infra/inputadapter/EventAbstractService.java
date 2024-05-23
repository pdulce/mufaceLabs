package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.EventMessageBrokerInputPort;
import com.arch.mfc.infra.outputport.QueryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class EventAbstractService implements EventMessageBrokerInputPort {

    @Autowired
    QueryRepositoryInterface queryRepository;
    @Override
    public void insertEvent(String almacen, Map<String, Object> reg) {

    }

    @Override
    public List<Map<String, Object>> getAll(String almacen) {
        return null;
    }
}
