package com.arch.mfc.infra.event.aggregate;

import com.arch.mfc.infra.event.commands.GenericCommand;
import org.apache.poi.ss.formula.functions.T;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class SimpleAggregate {

    @AggregateIdentifier
    private String aggregateId;
    T data;

    public SimpleAggregate() {

    }

    @CommandHandler
    public SimpleAggregate(GenericCommand command) {

        apply(new GenericCommand(command.getId(), command.getData()));
    }

    @EventSourcingHandler
    public void on(GenericCommand event) {
        this.aggregateId = event.getId();
        this.data = event.getData();
        // Handle event
    }

}
