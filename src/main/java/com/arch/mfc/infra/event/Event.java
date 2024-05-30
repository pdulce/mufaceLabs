package com.arch.mfc.infra.event;

import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;

@Data
public class Event<T> {

    public static final String EVENT_TOPIC = "topic-command-event";
    public static final String EVENT_TYPE_DELETE = "delete";
    public static final String EVENT_TYPE_CREATE = "create";
    public static final String EVENT_TYPE_UPDATE = "update";
    private String id;
    private String almacen;
    private String typeEvent;
    private Timestamp occurredOn;
    private T data;

    public Event() {
    }
    public Event(String almacen, String id, String typeEvent, T data) {
        this.almacen = almacen;
        this.id = id;
        this.typeEvent = typeEvent;
        this.occurredOn = new Timestamp(Calendar.getInstance().getTimeInMillis());
        this.data = data;
    }

}
