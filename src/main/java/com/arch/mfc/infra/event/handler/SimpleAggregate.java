package com.arch.mfc.infra.event.handler;

import com.arch.mfc.infra.event.Command;
import com.arch.mfc.infra.event.CommandEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class SimpleAggregate implements AggregateRoot<String> {

    @AggregateIdentifier
    private String aggregateId;

    public SimpleAggregate() {}

    @Override
    public String getIdentifier() {
        return aggregateId;
    }
    @CommandHandler
    public SimpleAggregate(Command command) {
        apply(new CommandEvent(command.getId(), command.getData()));
    }

    @EventSourcingHandler
    public void on(CommandEvent event) {
        this.aggregateId = event.getId();
        // Handle event
    }

}
