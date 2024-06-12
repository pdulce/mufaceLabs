package com.mfc.infra.input.adapter;

import com.mfc.infra.configuration.EventBrokerProperties;
import com.mfc.infra.event.Event;
import com.mfc.infra.input.port.EventStoreInputPort;
import com.mfc.infra.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "arch.eventbroker.active", havingValue = "true", matchIfMissing = false)
public class EventStoreConsumerAdapter implements EventStoreInputPort {

    Logger logger = LoggerFactory.getLogger(EventStoreConsumerAdapter.class);

    @Autowired
    EventBrokerProperties eventBrokerProperties;
    protected static final String GROUP_ID = "event-adapter";

    @Autowired
    RedisTemplate<String, Event<?>> redisTemplate;

    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> eventArch) {
        procesarEvento(eventArch);
    }

    public void procesarEvento(Event<?> eventArch) {
        if (!eventBrokerProperties.isActive()) {
            logger.error("Debe tener activa la configuración de uso de mensajería en la arquitectura");
            return;
        }
        this.saveEvent(eventArch.getContextInfo().getApplicationId(), eventArch.getContextInfo().getAlmacen(),
                eventArch.getId(), eventArch);
    }

    public void saveEvent(String applicationId, String store, String id, Event<?> eventArch) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        if (hashOps.entries(ConversionUtils.getKeyAlmacen(applicationId,store)).get(id) == null) {
            List<Object> agregados = new ArrayList<>();
            hashOps.put(ConversionUtils.getKeyAlmacen(applicationId,store), id, agregados);
        }
        List<Object> listaDelAgregado = hashOps.entries(ConversionUtils.getKeyAlmacen(applicationId,store)).get(id);
        listaDelAgregado.add(eventArch);
        hashOps.put(ConversionUtils.getKeyAlmacen(applicationId,store), id, listaDelAgregado);
    }

    public void update(String applicationId, String store, String id, String idObjectSearched, Event<?> eventArch) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        if (hashOps.entries(ConversionUtils.getKeyAlmacen(applicationId, store)).get(id) == null) {
            List<Object> agregados = new ArrayList<>();
            hashOps.put(ConversionUtils.getKeyAlmacen(applicationId, store), id, agregados);
        }
        List<Object> listaDelAgregado = hashOps.entries(ConversionUtils.getKeyAlmacen(applicationId,store)).get(id);
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
        hashOps.put(ConversionUtils.getKeyAlmacen(applicationId,ConversionUtils.getKeyAlmacen(applicationId,store)),
                id, listaDelAgregado);
    }

    public List<Object> findAllByAppAndStoreAndAggregatedId(String applicationId, String store, String id) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(ConversionUtils.getKeyAlmacen(applicationId,store)).get(id);
    }

    public Map<String, List<Object>> findAllByAppAndStore(String applicationId, String store) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(ConversionUtils.getKeyAlmacen(applicationId,store));
    }


}
