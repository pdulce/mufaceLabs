package com.mfc.infra.input.port;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface QueryInputPort<T> {
    
    void deleteReg(String id);
    void saveReg(LinkedHashMap deserialized, Class<T> entityClass);

    T findById(String id);

    List<T> findAll();



}
