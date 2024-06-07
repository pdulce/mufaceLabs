package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

public interface SagaStepPort {

    String getSagaName();

    int getOrderStepInSaga();

    public String getTypeOrOperation();

    void doSagaOperation(Event<?> event);

    void doSagaCompensation(Event<?> event);

}
