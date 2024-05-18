package com.arch.mfc.infra.event.handler;

import com.arch.mfc.infra.event.ComandCanonic;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;


import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class SimpleAggregate {

    @AggregateIdentifier
    private String aggregateId;
    private String data;

    public SimpleAggregate() {

    }

    @CommandHandler
    public SimpleAggregate(ComandCanonic command) {
        apply(new ComandCanonic(command.getId(), command.getData()));
    }

    @EventSourcingHandler
    public void on(ComandCanonic event) {
        this.aggregateId = event.getId();
        this.data = event.getData();
        // Handle event
    }

}
