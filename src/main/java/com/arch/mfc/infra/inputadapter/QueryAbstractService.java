package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.QueryMessageBrokerInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public abstract class QueryAbstractService implements QueryMessageBrokerInputPort {

    @Autowired
    QueryMessageBrokerInputPort queryRepository;

    public abstract Map<String,Class<?>> getClasses();

    @Override
    public void deleteReg(String almacen, Map<String, Class<?>> reg) {
        queryRepository.deleteReg( (String) reg.get("id").cast(String.class), reg );
    }

    @Override
    public void updateReg(String almacen, Map<String, Class<?>> reg) {
        queryRepository.updateReg( almacen, reg );
    }

    @Override
    public void insertReg(String almacen, Map<String, Class<?>> reg) {
        queryRepository.insertReg( almacen, reg );
    }

    @Override
    public List<Map<String, Object>> getAll(String almacen) {
        return queryRepository.getAll( almacen );
    }

}
