package com.arch.mfc.infra.events.port;

import com.arch.mfc.infra.events.EventArch;

public interface EventPublisher {
    void publish(String topic, EventArch<?> eventArch);
}
