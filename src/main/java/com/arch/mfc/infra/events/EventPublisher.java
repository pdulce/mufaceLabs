package com.arch.mfc.infra.events;

public interface EventPublisher {
    void publish(String topic, EventArch<?> eventArch);
}
