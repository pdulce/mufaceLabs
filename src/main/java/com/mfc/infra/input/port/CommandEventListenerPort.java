package com.mfc.infra.input.port;

import com.mfc.infra.event.Event;

public interface CommandEventListenerPort {

    void listen(Event<?> eventArch);
    void procesarEvento(Event<?> eventArch);
}
