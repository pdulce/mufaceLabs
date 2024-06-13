package com.mfc.infra.output.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.output.port.EventStoreInputPort;
import com.mfc.infra.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class EventStoreConsumerAdapter implements EventStoreInputPort {

    Logger logger = LoggerFactory.getLogger(EventStoreConsumerAdapter.class);

    @Autowired
    RedisTemplate<String, Event<?>> redisTemplate;

    @Override
    public void saveEvent(String typeStore, String applicationId, String store, String id, Event<?> eventArch) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        if (hashOps.entries(ConversionUtils.getKeyAlmacen(typeStore,applicationId,store)).get(id) == null) {
            List<Object> agregados = new ArrayList<>();
            hashOps.put(ConversionUtils.getKeyAlmacen(typeStore,applicationId,store), id, agregados);
        }
        List<Object> listaDelAgregado = hashOps.entries(ConversionUtils.getKeyAlmacen(typeStore,
                applicationId,store)).get(id);
        listaDelAgregado.add(eventArch);
        hashOps.put(ConversionUtils.getKeyAlmacen(typeStore,applicationId,store), id, listaDelAgregado);
        logger.info("evento: " + eventArch.getId() + " grabado correctamente en tipo de store: " + typeStore
        + " : aplicación: " +  applicationId + " almacén : " + store + " en el agregado: " + id);
    }

    @Override
    public void update(String typeStore, String applicationId, String store, String id, String idObjectSearched,
                       Event<?> eventArch) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        if (hashOps.entries(ConversionUtils.getKeyAlmacen(typeStore,applicationId, store)).get(id) == null) {
            List<Object> agregados = new ArrayList<>();
            hashOps.put(ConversionUtils.getKeyAlmacen(typeStore,applicationId, store), id, agregados);
        }
        List<Object> listaDelAgregado = hashOps.entries(ConversionUtils.getKeyAlmacen(typeStore,
                applicationId,store)).get(id);
        boolean found = false;
        int i = 0;
        while (!found && i < listaDelAgregado.size()) {
            Object obj = listaDelAgregado.get(i++);
            Event event = ConversionUtils.convertMapToObject(obj, Event.class);
            if (event.getId().contentEquals(idObjectSearched)) {
                listaDelAgregado.remove(obj);
                found = true;
            }
        }
        listaDelAgregado.add(eventArch);
        hashOps.put(ConversionUtils.getKeyAlmacen(typeStore,applicationId,store), id, listaDelAgregado);
        logger.info("evento: " + eventArch.getId() + " actualizado correctamente en tipo de store: " + typeStore
                + " : aplicación: " +  applicationId + " almacén : " + store + " el agregado: " + id);
    }

    @Override
    public Object findAllByAppAndStoreAndAggregatedId(String typeStore, String applicationId, String store,
                                                              String id) {
        HashOperations<String, String, List<Event<?>>> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(ConversionUtils.getKeyAlmacen(typeStore,applicationId,store)).get(id);
    }

    @Override
    public List<Object> findAllByAppAndStore(String typeStore, String applicationId, String store) {
        List<Object> allEvents = new ArrayList<>();

        // Obtener todas las claves que empiecen por typeStore
        ScanOptions scanOptions = ScanOptions.scanOptions().match(typeStore + "*").build();
        for (String key : redisTemplate.keys("*")) {
            if (key.startsWith(typeStore + ":" + applicationId + ":" + store)) {
                Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
                for (Map.Entry<Object, Object> entry : entries.entrySet()) {
                    Object event = entry.getValue();
                    allEvents.add(event);
                }
            }
        }
        return allEvents;
    }

    @Override
    public List<Object> findAllByApp(String typeStore, String applicationId) {
        List<Object> allEvents = new ArrayList<>();

        // Obtener todas las claves que empiecen por typeStore
        ScanOptions scanOptions = ScanOptions.scanOptions().match(typeStore + "*").build();
        for (String key : redisTemplate.keys("*")) {
            if (key.startsWith(typeStore + ":" + applicationId)) {
                Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
                for (Map.Entry<Object, Object> entry : entries.entrySet()) {
                    Object event = entry.getValue();
                    allEvents.add(event);
                }
            }
        }
        return allEvents;
    }


    @Override
    public List<Object> findAllStoreType(String typeStore) {
        List<Object> allEvents = new ArrayList<>();

        // Obtener todas las claves que empiecen por typeStore
        ScanOptions scanOptions = ScanOptions.scanOptions().match(typeStore + "*").build();
        for (String key : redisTemplate.keys("*")) {
            if (key.startsWith(typeStore)) {
                Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
                for (Map.Entry<Object, Object> entry : entries.entrySet()) {
                    Object event = entry.getValue();
                    allEvents.add(event);
                }
            }
        }
        return allEvents;
    }

}
