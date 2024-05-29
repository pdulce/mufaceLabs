package com.arch.mfc.infra.events;

import com.arch.mfc.infra.inputport.EventStoreInputPort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventStore implements EventStoreInputPort {

    private final RedisTemplate<String, Event<?>> redisTemplate;

    public EventStore(RedisTemplate<String, Event<?>> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveEvent(Event<?> event) {
        redisTemplate.opsForList().leftPush(Event.EVENT_TOPIC, event);
        redisTemplate.opsForHash().put("eventsById", event.getId(), event);
    }

    public Event<?> getById(String id) {

        return (Event<?>) redisTemplate.opsForHash().get("eventsById", id);
    }

    public List<Event<?>> getAll() {
        return redisTemplate.opsForList().range(Event.EVENT_TOPIC, 0, -1)
                .stream()
                .map(event -> (Event<?>) event)
                .collect(Collectors.toList());
    }

}