package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

public interface CommandEventPublisherPort {
    void publish(String topic, Event<?> eventArch);
}
