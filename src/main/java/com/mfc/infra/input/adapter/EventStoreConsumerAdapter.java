package com.mfc.infra.input.adapter;

import com.mfc.infra.configuration.EventBrokerProperties;
import com.mfc.infra.event.Event;
import com.mfc.infra.input.port.EventConsumer;
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
public class EventStoreConsumerAdapter implements EventStoreInputPort, EventConsumer {

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
        this.saveEvent(eventArch.getContextInfo().getAlmacen(), eventArch.getId(), eventArch);
    }

    public void saveEvent(String almacen, String id, Event<?> eventArch) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        if (hashOps.entries(almacen).get(id) == null) {
            List<Object> agregados = new ArrayList<>();
            hashOps.put(almacen, id, agregados);
        }
        List<Object> listaDelAgregado = hashOps.entries(almacen).get(id);
        listaDelAgregado.add(eventArch);
        hashOps.put(almacen, id, listaDelAgregado);
    }

    public void update(String almacen, String id, String idObjectSearched, Event<?> eventArch) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        if (hashOps.entries(almacen).get(id) == null) {
            List<Object> agregados = new ArrayList<>();
            hashOps.put(almacen, id, agregados);
        }
        List<Object> listaDelAgregado = hashOps.entries(almacen).get(id);
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
        hashOps.put(almacen, id, listaDelAgregado);
    }

    public List<Object> findById(String almacen, String id) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(almacen).get(id);
    }

    public Map<String, List<Object>> findAll(String almacen) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(almacen);
    }

    public void deleteAll(String almacen) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        hashOps.delete(almacen);
    }


}
