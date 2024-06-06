package com.mfc.infra.inputport;

import com.mfc.infra.event.Event;

public interface EventConsumer {

    void listen(Event<?> event);

    void procesarEvento(Event<?> event);

}
