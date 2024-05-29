package com.arch.mfc.infra.events;

import com.arch.mfc.infra.events.Event;

public interface EventPublisher {
    void publish(String topic, Event<?> event);
}
