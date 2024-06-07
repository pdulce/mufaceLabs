package com.mfc.infra.event;

import lombok.Data;

@Data
public class SagaStepInfo {
    private String sagaName;
    private Long transactionIdentifier;
    private int nextStepNumberToProccess;
    private int lastStepNumberProccessed; // : -1 es que ha fallado ese last step procesado (el intento de hacer el next)
    private boolean doCompensateOp;
    private boolean lastStep;

    private SagaStepInfo(){}

    public SagaStepInfo(String sagaName, Integer nextStepNumberToProccess, Long transactionIdentifier){
        this.sagaName = sagaName;
        this.nextStepNumberToProccess = nextStepNumberToProccess;
        this.transactionIdentifier = transactionIdentifier;
        this.doCompensateOp = false;
        this.lastStep = false;
    }


}
