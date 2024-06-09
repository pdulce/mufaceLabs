package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

public interface SagaOrchestratorPort<T> {

    Event startSaga(String sagaName, String operation, T data);

    public String getLastStateOfTansactionInSaga(String saganame, String transaccId);

    String DO_OPERATION = "exec-ope";
    String SAGA_FROM_STEP_TOPIC = "saga-from-step-topic";

}
