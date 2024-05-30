package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.EventSourcingDocumentInputPort;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


//@Service
public class EventDocumentInputAdapter implements EventSourcingDocumentInputPort<T> {

    //@Autowired
    MongoRepository<T, String> eventRepositoryInterface;


    @Override
    public void insertEvent(T obj) {
        eventRepositoryInterface.save(obj);
    }

    @Override
    public List<T> findAll() {
        return eventRepositoryInterface.findAll();
    }
}
