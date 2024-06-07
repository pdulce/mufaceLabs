package com.mfc.infra.event;

import lombok.Data;

@Data
public class SagaStepInfo {
    private String sagaName;
    private Long transactionIdentifier;
    private int nextStepNumberToProccess;
    private int stepNumberProccessed;
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
