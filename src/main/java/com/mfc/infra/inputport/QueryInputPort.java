package com.mfc.infra.inputport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface QueryInputPort<T> {
    
    void deleteReg(String id);
    void saveReg(LinkedHashMap deserialized, Class<T> entityClass);

    Map<String, Object> findById(String id);
    List<Map<String, Object>> findAll();



}
