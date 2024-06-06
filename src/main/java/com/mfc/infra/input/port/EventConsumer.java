package com.mfc.infra.input.port;

import com.mfc.infra.event.Event;

public interface EventConsumer {

    void listen(Event<?> event);

    void procesarEvento(Event<?> event);

}
