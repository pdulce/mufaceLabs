package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.CQRSMessageBrokerInputPort;
import com.arch.mfc.infra.outputport.QueryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class MessageBrokerServiceAbstract implements CQRSMessageBrokerInputPort {

    @Autowired
    QueryRepositoryInterface queryRepository;


    public abstract Map<String,Class<?>> getClasses();

    @Override
    public void deleteReg(String table, Map<String, Object> reg) {
        queryRepository.delete( (String) reg.get("id"), getClasses().get( table ) );
    }

    @Override
    public void updateReg(String table, Map<String, Object> reg) {
        queryRepository.save( reg, getClasses().get( table ) );
    }

    @Override
    public void insertReg(String table, Map<String, Object> reg) {
        queryRepository.save( reg, getClasses().get( table ) );
    }

    @Override
    public List<Map<String, Object>> getAll(String table) {
        return queryRepository.getAll( getClasses().get( table ) );
    }

}
