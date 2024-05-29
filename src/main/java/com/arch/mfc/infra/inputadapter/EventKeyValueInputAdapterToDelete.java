package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.EventSourcingKeyValueInputPort;
import com.arch.mfc.infra.outputadapter.nonrelational.RepositoryRedisImplToDelete;

import java.util.List;
import java.util.Map;


//@Service
public class EventKeyValueInputAdapterToDelete implements EventSourcingKeyValueInputPort {

    //@Autowired
    RepositoryRedisImplToDelete queryRepository;


    @Override
    public void saveEvent(String almacen, Map<String, Object> reg) {
        queryRepository.save( reg, almacen );
    }

    @Override
    public List<Map<String, Object>> getAll(String almacen) {
        return queryRepository.getAll( almacen );
    }

}
