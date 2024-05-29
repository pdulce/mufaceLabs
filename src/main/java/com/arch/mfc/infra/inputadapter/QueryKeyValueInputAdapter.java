package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.QueryCQRSKeyValueInputPort;
import com.arch.mfc.infra.outputadapter.nonrelational.RepositoryRedisImplToDelete;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class QueryKeyValueInputAdapter implements QueryCQRSKeyValueInputPort {

    @Autowired
    RepositoryRedisImplToDelete queryRepository;

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
