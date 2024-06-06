package com.mfc.infra.saga;

import org.apache.poi.ss.formula.functions.T;

public interface SagaCoordinatorPort {

    void initSaga(T data);

    void endSaga(T data);

}
