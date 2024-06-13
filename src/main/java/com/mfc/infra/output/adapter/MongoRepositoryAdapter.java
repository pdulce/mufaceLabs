package com.mfc.infra.output.adapter;

import com.mfc.infra.configuration.ConfigProperties;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.port.MongoRepositoryPort;
import com.mfc.infra.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public abstract class MongoRepositoryAdapter<T> implements MongoRepositoryPort<T> {
    Logger logger = LoggerFactory.getLogger(MongoRepositoryAdapter.class);

    @Autowired
    private MongoMappingContext mongoMappingContext;
    @Autowired
    ConfigProperties configProperties;
    @Autowired
    MongoRepository<T, String> repository;

    public abstract void listen(Event<?> eventArch);

    public String getCollectionName(Class<?> documentClass) {
        return mongoMappingContext.getPersistentEntity(documentClass) == null ? "unknown" :
                mongoMappingContext.getPersistentEntity(documentClass).getCollection();
    }

    public void procesarEvento(Event<?> event) {
        if (!configProperties.isEventBrokerActive()) {
            logger.error("Debe tener activa la configuración de uso de mensajería en la arquitectura");
            return;
        }
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

        String collectionName = getCollectionName(entityClass);

        if (event.getContextInfo() != null
                && configProperties.getApplicationId().contentEquals(event.getContextInfo().getApplicationId())
                && event.getContextInfo().getAlmacen() != null
                && collectionName.equals(event.getContextInfo().getAlmacen())) {
            if (event.getInnerEvent().getTypeOfEvent().contentEquals(Event.EVENT_TYPE_CREATE)
                    || event.getInnerEvent().getTypeOfEvent().contentEquals(Event.EVENT_TYPE_UPDATE)) {
                saveReg((LinkedHashMap) event.getInnerEvent().getData(), entityClass);
            } else if (event.getInnerEvent().getTypeOfEvent().contentEquals(Event.EVENT_TYPE_DELETE)) {
                this.deleteReg(event.getId());
            }
        } // else::  //dejo pasar este mensaje porque no es para este consumidor
    }

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