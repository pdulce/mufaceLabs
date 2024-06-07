package com.mfc.infra.saga.port;

import com.mfc.infra.event.Event;

public interface SagaStepPort {

    Integer getOrderStepInSaga();

    void doSagaOperation(Event<?> event);

    void doSagaCompensation(Event<?> event);

}
