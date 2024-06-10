package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

public interface SagaStepPort<T, ID> {

    String getSagaName();

    int getOrderStepInSaga();

    boolean isLastStepInSaga();

    public String getTypeOrOperation();

    Object doSagaOperation(Event event) throws Throwable;

    Object doSagaCompensation(Event event) throws Throwable;

}
