package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.inputport.QueryCQRSDocumentInputPort;
import com.arch.mfc.infra.outputadapter.nonrelational.MongoImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryDocumentInputAdapter implements QueryCQRSDocumentInputPort<T> {

    @Autowired
    MongoImpl<T> mongoImplementation;


    @Override
    public void deleteReg(Map<String, Object> reg, Class<T> clazz) {
        mongoImplementation.delete((String) reg.get("id"));
    }

    @Override
    public void updateReg(Map<String, Object> reg, Class<T> clazz) {
        mongoImplementation.save(reg, clazz);
    }

    @Override
    public void insertReg(Map<String, Object> reg, Class<T> clazz) {
        mongoImplementation.save(reg, clazz);
    }

    @Override
    public List<Map<String, Object>> getAll(Class<T> clazz) {
        return mongoImplementation.getAll(clazz);
    }

}