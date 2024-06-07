package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

public interface SagaOrchestratorPort<T> {

    Event<?> startSaga(String sagaName, String operation, T data);

    String SAGA_DO_STEP_OPERATION_TOPIC = "saga-order-operation-topic";
    String SAGA_FROM_STEP_TOPIC = "saga-from-step-topic";
    String DO_OPERATION_TOPIC_NAME = "-do-operation-";
}
