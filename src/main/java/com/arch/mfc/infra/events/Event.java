package com.arch.mfc.infra.events;

import java.time.Instant;

public class Event<T> {

    public static final String EVENT_TOPIC = "events";
    public static final String EVENT_TYPE_DELETE = "delete";
    public static final String EVENT_TYPE_CREATE = "create";
    public static final String EVENT_TYPE_UPDATE = "update";
    private final String id;
    private final String typeEvent;
    private final Instant occurredOn;
    private final T data;

    public Event(String id, String typeEvent, T data) {
        this.id = id;
        this.typeEvent = typeEvent;
        this.occurredOn = Instant.now();
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }

    public T getData() {
        return data;
    }
}
