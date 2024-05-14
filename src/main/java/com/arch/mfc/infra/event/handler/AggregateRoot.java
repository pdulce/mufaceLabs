package com.arch.mfc.infra.event.handler;

public interface AggregateRoot<T> {
    T getIdentifier();

}

