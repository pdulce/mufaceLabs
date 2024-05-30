package com.arch.mfc.infra.events.adapter;

import com.arch.mfc.infra.events.EventArch;
import com.arch.mfc.infra.events.port.EventStoreInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RedisHash
@Service
public class RedisEventStore implements EventStoreInputPort {

    @Autowired
    RedisTemplate<String, EventArch<?>> redisTemplate;

    public void saveEvent(EventArch<?> eventArch) {
        Map<String, Object> mapaData = ConversionUtils.convertLinkedHashMapToMap(eventArch.getData());
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("operation", eventArch.getTypeEvent());
        mapa.put("timestamp", eventArch.getOccurredOn());
        mapa.put("data", mapaData.get("data"));
        String jsonConverted = ConversionUtils.map2Jsonstring(mapa);
        //redisTemplate.opsForList().leftPush(EventArch.EVENT_TOPIC, jsonConverted);
        redisTemplate.opsForHash().put(eventArch.getAlmacen(), eventArch.getId(), jsonConverted);
    }

    public EventArch<?> findById(String almacen, String id) {
        String recovered = (String) redisTemplate.opsForHash().get(almacen, id);
        //Map<String, Object> mapa = ConversionUtils.jsonstring2Map(recovered);
        //Map<String, Object> mapaData = (Map<String, Object>) mapa.get("data");
        //ConversionUtils.jsonStringToObject(recovered, ?);
        return null;
    }

    public List<EventArch<?>> findAll(String almacen) {
        return redisTemplate.opsForList().range(almacen, 0, -1)
                .stream()
                .map(event -> (EventArch<?>) event)
                .collect(Collectors.toList());
        /*
         return redisTemplate.opsForHash()
                .values( "eventsById" )
                .stream()
                .map( el -> ConversionUtils.jsonstring2Map( (String) el ) )
                .collect( Collectors.toList() );
         */
    }

}