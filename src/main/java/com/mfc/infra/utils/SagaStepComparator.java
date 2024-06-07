package com.mfc.infra.utils;

import com.mfc.infra.event.Event;

import java.util.Comparator;

public class SagaStepComparator implements Comparator<Object> {

    @Override
    public int compare(final Object data1, final Object data2) {
        Event<?> event1 = (Event<?>) data1;
        Event<?> event2 = (Event<?>) data2;
        int retorno = 0;
        if (event1.getSagaStepInfo().getLastStepNumberProccessed() > event2.getSagaStepInfo().getLastStepNumberProccessed()) {
            retorno = -1;
        } else if (event1.getSagaStepInfo().getLastStepNumberProccessed() < event2.getSagaStepInfo().getLastStepNumberProccessed()) {
            retorno = 1;
        }
        return retorno;
    }

}
