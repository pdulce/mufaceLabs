package com.mfc.infra.input.adapter;

import com.mfc.infra.configuration.ConfigProperties;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.port.MongoRepositoryPort;
import com.mfc.infra.input.port.QueryListenerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;

public abstract class QueryListenerAdapter<T> implements QueryListenerPort<T> {
    Logger logger = LoggerFactory.getLogger(QueryListenerAdapter.class);

    @Autowired
    MongoRepositoryPort mongoRepositoryPort;

    @Autowired
    private MongoMappingContext mongoMappingContext;
    @Autowired
    ConfigProperties configProperties;


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
                this.mongoRepositoryPort.saveReg((LinkedHashMap) event.getInnerEvent().getData(), entityClass);
            } else if (event.getInnerEvent().getTypeOfEvent().contentEquals(Event.EVENT_TYPE_DELETE)) {
                this.mongoRepositoryPort.deleteReg(event.getId());
            }
        } // else::  //dejo pasar este mensaje porque no es para este consumidor
    }


}