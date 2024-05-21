package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.CQRSMessageBrokerInputPort;
import com.arch.mfc.infra.outputport.QueryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public abstract class QueryAbstractService implements CQRSMessageBrokerInputPort {

    @Autowired
    QueryRepositoryInterface queryRepository;


    public abstract Map<String,Class<?>> getClasses();

    @Override
    public void deleteReg(String almacen, Map<String, Object> reg) {
        queryRepository.delete( (String) reg.get("id"), getClasses().get( almacen ) );
    }

    @Override
    public void updateReg(String almacen, Map<String, Object> reg) {
        try {
            queryRepository.save( reg, getClasses().get( almacen ) );
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertReg(String almacen, Map<String, Object> reg) {
        try {
            queryRepository.save( reg, getClasses().get( almacen ) );
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> getAll(String almacen) {
        return queryRepository.getAll( getClasses().get( almacen ) );
    }

}
