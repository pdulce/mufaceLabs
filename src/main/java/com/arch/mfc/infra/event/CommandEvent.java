package com.arch.mfc.infra.event;


public class CommandEvent<T> {

    private final String id;
    private final T data;

    public CommandEvent(String id, T data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public T getData() {
        return data;
    }
}

