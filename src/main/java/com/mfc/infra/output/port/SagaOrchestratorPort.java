package com.mfc.infra.output.port;

import com.mfc.infra.event.Event;

public interface SagaOrchestratorPort<T> {

    Event<?> startSaga(String sagaName, String operation, T data);

    String SAGA_ORDER_OPERATION_TOPIC = "saga-order-operation-topic";
    String SAGA_FROM_STEP_TOPIC = "saga-from-step-topic";

}
