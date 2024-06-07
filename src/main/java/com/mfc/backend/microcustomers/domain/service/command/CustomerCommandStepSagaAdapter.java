package com.mfc.backend.microcustomers.domain.service.command;

import com.mfc.backend.microcustomers.domain.model.command.Customer;
import com.mfc.infra.event.Event;
import com.mfc.infra.exceptions.NotExistException;
import com.mfc.infra.output.adapter.CommandAdapter;
import com.mfc.infra.output.adapter.CommandStepSagaAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommandStepSagaAdapter extends CommandStepSagaAdapter<Customer> {

    /** personalized operations not in infra : acceder al repositorio de la infra y consultar **/

    public String getDocumentEntityClassname() {
        return "CustomerDocument";
    }

    @Override
    public void listen(Event<?> event) {
        super.processStepEvent(event);
    }

    @Override
    public String getSagaName() {
        return "misaga";
    }

    @Override
    public int getOrderStepInSaga() {
        return 1;
    }

    @Override
    public void doSagaOperation(Event<?> event) {
        try {
            this.insert((Customer) event.getInnerEvent().getData());
        } catch (Throwable exc) {
            event.getInnerEvent().setTypeEvent(Event.EVENT_FAILED_OPERATION);
            logger.error("doSagaOperation failed: Cause ", exc);
        }
    }

    @Override
    public void doSagaCompensation(Event<?> event) {
        try {
            this.delete((Customer) event.getInnerEvent().getData());
        } catch (NotExistException notExistException) {
            logger.error("doSagaCompensation failed: Cause ", notExistException);
            event.getInnerEvent().setTypeEvent(Event.EVENT_FAILED_OPERATION);
        }
    }


}
