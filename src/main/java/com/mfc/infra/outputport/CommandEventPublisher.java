package com.mfc.infra.outputport;

import com.mfc.infra.event.Event;

public interface CommandEventPublisher {
    void publish(String topic, Event<?> eventArch);
}
