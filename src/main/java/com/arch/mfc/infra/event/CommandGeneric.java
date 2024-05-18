package com.arch.mfc.infra.event;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CommandGeneric<T> {

    @TargetAggregateIdentifier
    private final String id;

    private final T data;

    public CommandGeneric(String id, T data) {
        this.id = id;
        this.data = data;
    }

}
