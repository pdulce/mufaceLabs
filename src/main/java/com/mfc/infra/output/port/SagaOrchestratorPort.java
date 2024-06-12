package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

public interface SagaOrchestratorPort<T> {

    Event startSaga(String applicationId, String sagaName, String operation, T data);

    String[] getLastStateOfTansactionInSaga(String applicationId, String saganame, String transaccId);

    String DO_OPERATION = "exec-ope-";
    String SAGA_FROM_STEP_TOPIC = "saga-from-step-topic";

}
