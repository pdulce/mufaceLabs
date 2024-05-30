package com.arch.mfc.infra.inputport;

import com.arch.mfc.infra.event.Event;

public interface EventConsumer {

    void listen(Event<?> event);

}
