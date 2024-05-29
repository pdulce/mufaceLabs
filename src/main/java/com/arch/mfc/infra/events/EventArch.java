package com.arch.mfc.infra.events;

import lombok.Data;

import java.time.Instant;

@Data
public class EventArch<T> {

    public static final String EVENT_TOPIC = "events";
    public static final String EVENT_TYPE_DELETE = "delete";
    public static final String EVENT_TYPE_CREATE = "create";
    public static final String EVENT_TYPE_UPDATE = "update";
    private String id;
    private String almacen;
    private String typeEvent;
    private Instant occurredOn;
    private T data;

    public EventArch() {
    }
    public EventArch(String id, String typeEvent, T data) {
        this.id = id;
        this.typeEvent = typeEvent;
        this.occurredOn = Instant.now();
        this.data = data;
    }

}
