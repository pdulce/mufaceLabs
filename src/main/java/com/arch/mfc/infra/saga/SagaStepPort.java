package com.arch.mfc.infra.saga;

import org.apache.poi.ss.formula.functions.T;

public interface SagaStepPort {

    void compensationOperation(T data);

    void consolidationOperation(T data);

}
