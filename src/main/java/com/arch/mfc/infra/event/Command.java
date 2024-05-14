package com.arch.mfc.infra.event;

public class Command<T> {

    private final String id;
    private final T data;

    public Command(String id, T data) {
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
