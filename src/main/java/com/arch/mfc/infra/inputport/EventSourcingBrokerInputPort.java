package com.arch.mfc.infra.inputport;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface EventSourcingBrokerInputPort {

    void insertEvent(Map<String, Object> reg, Class<T> clazz);

    List<Map<String, Object>> getAll(Class<T> clazz);

}
