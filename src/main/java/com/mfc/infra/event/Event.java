package com.mfc.infra.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class Event<T> implements Serializable {

    public static final String EVENT_TOPIC = "topic-command-event";
    public static final String EVENT_TYPE_DELETE = "delete";
    public static final String EVENT_TYPE_CREATE = "create";
    public static final String EVENT_TYPE_UPDATE = "update";
    private String id;
    private ContextInfo contextInfo;
    private InnerEvent<T> innerEvent;

    public Event() {
    }
    public Event(String almacen, String author, String applicationId,
                 String id, String typeEvent, T data) {
        this.contextInfo = new ContextInfo(almacen, author, applicationId);
        this.id = id;
        this.innerEvent = new InnerEvent<T>(typeEvent, data);
    }

}
