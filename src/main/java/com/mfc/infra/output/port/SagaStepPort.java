package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

public interface SagaStepPort {

    void listen(Event<?> event);
    String getSagaName();

    int getOrderStepInSaga();

    void doSagaOperation(Event<?> event);

    void doSagaCompensation(Event<?> event);

}
