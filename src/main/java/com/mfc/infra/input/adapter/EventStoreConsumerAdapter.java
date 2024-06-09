package com.mfc.infra.input.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.input.port.EventConsumer;
import com.mfc.infra.input.port.EventStoreInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventStoreConsumerAdapter implements EventStoreInputPort, EventConsumer {

    Logger logger = LoggerFactory.getLogger(EventStoreConsumerAdapter.class);
    protected static final String GROUP_ID = "event-adapter";

    @Autowired
    RedisTemplate<String, Event<?>> redisTemplate;

    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> eventArch) {
        procesarEvento(eventArch);
    }

    public void procesarEvento(Event<?> eventArch) {

        this.saveEvent(eventArch.getContextInfo().getAlmacen(), eventArch.getId(), eventArch);
    }

    public void saveEvent(String almacen, String id, Event<?> eventArch) {
        HashOperations<String, String, List<Object>> hashOps = redisTemplate.opsForHash();
        if (hashOps.entries(almacen).get(id) == null) {
            List<Object> agregados = new ArrayList<>();
            hashOps.put(almacen, id, agregados);
        }
        List<Object> agregados = hashOps.entries(almacen).get(id);
        agregados.add(eventArch);//.getInnerEvent());
        hashOps.put(almacen, id, agregados);
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
