package com.arch.mfc.infra.events;

import com.arch.mfc.infra.inputport.EventStoreInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RedisHash
@Service
public class RedisEventStore implements EventStoreInputPort {

    @Autowired
    RedisTemplate<String, EventArch<?>> redisTemplate;

    public void saveEvent(EventArch<?> eventArch) {
        redisTemplate.opsForList().leftPush(EventArch.EVENT_TOPIC, eventArch);
        redisTemplate.opsForHash().put("eventsById", eventArch.getId(), eventArch);
    }

    public EventArch<?> getById(String id) {

        return (EventArch<?>) redisTemplate.opsForHash().get("eventsById", id);
    }

    public List<EventArch<?>> getAll() {
        return redisTemplate.opsForList().range(EventArch.EVENT_TOPIC, 0, -1)
                .stream()
                .map(event -> (EventArch<?>) event)
                .collect(Collectors.toList());
    }

}