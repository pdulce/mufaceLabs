package com.mfc.infra.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class SagaStepInfo implements Serializable {
    private String sagaName;
    private Long transactionIdentifier;
    private int nextStepNumberToProccess;
    private int stateOfFinalization;
    private boolean doCompensateOp;
    private boolean lastStep;

    private SagaStepInfo(){}

    public SagaStepInfo(String sagaName, Integer nextStepNumberToProccess, Long transactionIdentifier){
        this.sagaName = sagaName;
        this.nextStepNumberToProccess = nextStepNumberToProccess;
        this.stateOfFinalization = 0; // este valor es que aún no se ha ejecutado esta operación
        this.transactionIdentifier = transactionIdentifier;
        this.doCompensateOp = false;
        this.lastStep = false;
    }


}
