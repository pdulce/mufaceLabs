package com.mfc.infra.input.adapter;

import com.mfc.infra.configuration.ArqConfigProperties;
import com.mfc.infra.dto.IArqDTO;
import com.mfc.infra.event.ArqEvent;
import com.mfc.infra.input.port.ArqQueryDomainListenerPort;
import com.mfc.infra.output.adapter.ArqMongoServiceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;

public abstract class ArqQueryDomainListenerAdapter<T, D extends IArqDTO, ID>
        implements ArqQueryDomainListenerPort<T, D, ID> {
    Logger logger = LoggerFactory.getLogger(ArqQueryDomainListenerAdapter.class);

    @Autowired
    ArqMongoServiceAdapter<T, D, ID> mongoRepositoryPort;

    @Autowired
    private MongoMappingContext mongoMappingContext;
    @Autowired
    ArqConfigProperties arqConfigProperties;

    @Override
    public abstract void listen(ArqEvent<?> eventArch);

    @Override
    public void procesarEvento(ArqEvent<?> event) {
        if (!arqConfigProperties.isEventBrokerActive()) {
            logger.error("Debe tener activa la configuración de uso de mensajería en la arquitectura");
            return;
        }
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

        String collectionName = getCollectionName(entityClass);

        if (event.getArqContextInfo() != null
                && arqConfigProperties.getApplicationId().contentEquals(event.getArqContextInfo().getApplicationId())
                && event.getArqContextInfo().getAlmacen() != null
                && collectionName.equals(event.getArqContextInfo().getAlmacen())) {
            if (event.getInnerEvent().getTypeOfEvent().contentEquals(ArqEvent.EVENT_TYPE_CREATE)
                    || event.getInnerEvent().getTypeOfEvent().contentEquals(ArqEvent.EVENT_TYPE_UPDATE)) {
                this.mongoRepositoryPort.crear((LinkedHashMap) event.getInnerEvent().getData(), entityClass);
            } else if (event.getInnerEvent().getTypeOfEvent().contentEquals(ArqEvent.EVENT_TYPE_DELETE)) {
                this.mongoRepositoryPort.borrar(event.getId());
            }
        } // else::  //dejo pasar este mensaje porque no es para este consumidor
    }

    private String getCollectionName(Class<?> documentClass) {
        return mongoMappingContext.getPersistentEntity(documentClass) == null ? "unknown" :
                mongoMappingContext.getPersistentEntity(documentClass).getCollection();
    }


}