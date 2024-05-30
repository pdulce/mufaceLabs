package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.event.Event;
import com.arch.mfc.infra.inputport.EventConsumer;
import com.arch.mfc.infra.inputport.EventStoreInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EventStoreConsumerAdapter implements EventStoreInputPort, EventConsumer {
    protected static final String GROUP_ID = "event-adapter";

    @Autowired
    RedisTemplate<String, Event<?>> redisTemplate;

    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> eventArch) {
        saveEvent(eventArch);
    }

    public void saveEvent(Event<?> eventArch) {
        Map<String, Object> mapaData = ConversionUtils.convertLinkedHashMapToMap(eventArch.getData());
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("operation", eventArch.getTypeEvent());
        mapa.put("timestamp", eventArch.getOccurredOn());
        mapa.put("data", mapaData);
        String jsonConverted = ConversionUtils.map2Jsonstring(mapa);
        redisTemplate.opsForHash().put(eventArch.getAlmacen(), eventArch.getId(), jsonConverted);
    }

    public Event<?> findById(String almacen, String id) {
        HashOperations<String, String, Event<?>> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(almacen).get(almacen);
    }

    public Map<String, Event<?>> findAll(String almacen) {
        HashOperations<String, String, Event<?>> hashOps = redisTemplate.opsForHash();
        return hashOps.entries(almacen);
    }


}
