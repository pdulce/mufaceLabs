package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.EventSourcingBrokerInputPort;
import com.arch.mfc.infra.outputport.EventRepositoryInterface;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventAbstractService<T> implements EventSourcingBrokerInputPort<T> {

    @Autowired
    EventRepositoryInterface<T> eventRepositoryInterface;


    @Override
    public void insertEvent(Map<String, Object> reg, Class<T> clazz) {
        eventRepositoryInterface.save(reg, clazz);
    }

    @Override
    public List<Map<String, Object>> getAll(Class<T> clazz) {
        return eventRepositoryInterface.getAll(clazz);
    }
}
