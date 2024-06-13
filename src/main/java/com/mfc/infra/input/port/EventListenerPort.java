package com.mfc.infra.input.port;

import com.mfc.infra.event.Event;

public interface EventListenerPort {

    void listen(Event<?> eventArch);
    void procesarEvento(Event<?> eventArch);
}
