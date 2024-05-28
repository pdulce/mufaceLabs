package com.arch.mfc.infra.inputadapter;

import java.time.Instant;

public abstract class Event<T> {
    private final String id;
    private final Instant occurredOn;
    private final T data;

    protected Event(String id, T data) {
        this.id = id;
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
