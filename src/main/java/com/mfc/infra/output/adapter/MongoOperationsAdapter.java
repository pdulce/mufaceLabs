package com.mfc.infra.output.adapter;

import com.mfc.infra.output.port.MongoOperationsPort;
import com.mfc.infra.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public abstract class MongoOperationsAdapter<T> implements MongoOperationsPort<T> {
    Logger logger = LoggerFactory.getLogger(MongoOperationsAdapter.class);

    @Autowired
    MongoRepository<T, String> repository;

    @Override
    public void saveReg(LinkedHashMap deserialized, Class<T> entityClass) {
        T document = ConversionUtils.jsonStringToObject(ConversionUtils.map2Jsonstring(deserialized), entityClass);
        this.repository.save(document);
    }

    @Override
    public void deleteReg(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public T findById(String id) {
        Optional<T> searched = this.repository.findById(id);
        return searched.isPresent() ? searched.get() : null;
    }

    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

}