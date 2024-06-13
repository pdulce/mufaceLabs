package com.mfc.infra.input.port;

import com.mfc.infra.event.Event;

public interface QueryListenerPort<T> {

    void listen(Event<?> eventArch);

}
