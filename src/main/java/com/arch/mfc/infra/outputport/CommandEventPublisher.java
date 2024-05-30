package com.arch.mfc.infra.outputport;

import com.arch.mfc.infra.event.Event;

public interface CommandEventPublisher {
    void publish(String topic, Event<?> eventArch);
}
