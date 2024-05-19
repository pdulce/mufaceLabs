package com.arch.mfc.infra.event.commands;

import lombok.Value;
import org.apache.poi.ss.formula.functions.T;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class GenericCommand {

    @TargetAggregateIdentifier
    private final String id;

    private T data;

    public GenericCommand(final String id, final T data) {
        this.id = id;
        this.data = data;
    }

}