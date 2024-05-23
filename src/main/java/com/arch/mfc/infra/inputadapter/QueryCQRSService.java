package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.QueryCQRSBrokerInputPort;
import com.arch.mfc.infra.outputadapter.nonrelational.RepositoryRedisImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryCQRSService implements QueryCQRSBrokerInputPort {

    @Autowired
    RepositoryRedisImpl queryRepository;

    @Override
    public void deleteReg(String almacen, Map<String, Object> reg) {
        queryRepository.delete( (String) reg.get("id"), almacen );
    }

    @Override
    public void updateReg(String almacen, Map<String, Object> reg) {
        queryRepository.save( reg, almacen );
    }

    @Override
    public void insertReg(String almacen, Map<String, Object> reg) {

        queryRepository.save( reg, almacen );
    }

    @Override
    public List<Map<String, Object>> getAll(String almacen) {

        return queryRepository.getAll( almacen );
    }

}
