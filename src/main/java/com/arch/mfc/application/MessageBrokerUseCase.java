package com.arch.mfc.application;

import java.util.List;
import java.util.Map;

import com.arch.mfc.domain.Customer;
import com.arch.mfc.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arch.mfc.infra.inputport.MessageBrokerInputPort;
import com.arch.mfc.infra.outputport.QueryRepository;

@Component
public class MessageBrokerUseCase implements MessageBrokerInputPort {

    @Autowired
    QueryRepository queryRepository;

    Map<String,Class<?>> classes = Map.of(
        "customer", Customer.class,
        "orders", Order.class
    );

    @Override
    public void deleteReg(String table, Map<String, Object> reg) {
        queryRepository.delete( (String) reg.get("id"), classes.get( table ) );
    }

    @Override
    public void updateReg(String table, Map<String, Object> reg) {
        queryRepository.save( reg, classes.get( table ) );
    }

    @Override
    public void insertReg(String table, Map<String, Object> reg) {
        queryRepository.save( reg, classes.get( table ) );
    }

    @Override
    public List<Map<String, Object>> getAll(String table) {
        return queryRepository.getAll( classes.get( table ) );
    }
    
}
