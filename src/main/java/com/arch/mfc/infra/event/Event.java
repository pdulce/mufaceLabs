package com.arch.mfc.infra.event;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

@Data
public class Event<T> implements Serializable {

    public static final String EVENT_TOPIC = "topic-command-event";
    public static final String EVENT_TYPE_DELETE = "delete";
    public static final String EVENT_TYPE_CREATE = "create";
    public static final String EVENT_TYPE_UPDATE = "update";
    private String id;
    private String almacen;
    private InnerEvent<T> innerEvent;

    public Event() {
    }
    public Event(String almacen, String id, String typeEvent, T data) {
        this.almacen = almacen;
        this.id = id;
        this.innerEvent = new InnerEvent<T>(typeEvent, data);
    }

}
