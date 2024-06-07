package com.mfc.infra.event;

import lombok.Data;

@Data
public class SagaStepInfo {

    private int stepNumber;
    private String sagaName;
    private Long transactionIdentifier;
    private boolean doCompensateOp;
    private boolean lastStep;

    private SagaStepInfo(){}

    public SagaStepInfo(String sagaName, Integer stepNumber, Long transactionIdentifier){
        this.sagaName = sagaName;
        this.stepNumber = stepNumber;
        this.transactionIdentifier = transactionIdentifier;
        this.doCompensateOp = false;
        this.lastStep = false;
    }


}
