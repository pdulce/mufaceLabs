package com.arch.mfc.application;

import java.util.List;
import java.util.Map;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arch.mfc.infra.inputport.CQRSMessageBrokerInputPort;
import com.arch.mfc.infra.outputport.QueryRepositoryInterface;

@Component
public class MessageBrokerService implements CQRSMessageBrokerInputPort {

    @Autowired
    QueryRepositoryInterface queryRepository;

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
