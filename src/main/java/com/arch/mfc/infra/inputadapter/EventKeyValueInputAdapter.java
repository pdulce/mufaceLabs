package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.EventSourcingKeyValueInputPort;
import com.arch.mfc.infra.outputadapter.nonrelational.RepositoryRedisImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class EventKeyValueInputAdapter implements EventSourcingKeyValueInputPort {

    @Autowired
    RepositoryRedisImpl queryRepository;


    @Override
    public void saveEvent(String almacen, Map<String, Object> reg) {
        queryRepository.save( reg, almacen );
    }

    @Override
    public List<Map<String, Object>> getAll(String almacen) {
        return queryRepository.getAll( almacen );
    }

}
