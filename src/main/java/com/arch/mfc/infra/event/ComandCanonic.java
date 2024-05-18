package com.arch.mfc.infra.event;


import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Value;

@Value
public class ComandCanonic {

    @TargetAggregateIdentifier
    private String id;

    private String data;

}