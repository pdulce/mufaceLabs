package com.arch.mfc.infra.events.port;

import com.arch.mfc.infra.events.adapter.Event;

public interface EventPublisher {
    void publish(String topic, Event<?> event);
}
