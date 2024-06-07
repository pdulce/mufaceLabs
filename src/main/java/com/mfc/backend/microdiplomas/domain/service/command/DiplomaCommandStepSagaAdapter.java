package com.mfc.backend.microdiplomas.domain.service.command;

import com.mfc.backend.microdiplomas.domain.model.command.Diploma;
import com.mfc.infra.event.Event;
import com.mfc.infra.exceptions.NotExistException;
import com.mfc.infra.output.adapter.CommandStepSagaAdapter;
import org.springframework.stereotype.Service;

@Service
public class DiplomaCommandStepSagaAdapter extends CommandStepSagaAdapter<Diploma> {

    /** personalized operations not in infra : acceder al repositorio de la infra y consultar **/

    public String getDocumentEntityClassname() {
        return null;
    } // este micro no usa CQRS

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
    public String getTypeOrOperation() {
        return Event.EVENT_TYPE_CREATE;
    }

    @Override
    public void doSagaOperation(Event<?> event) {
        try {
            this.insert((Diploma) event.getInnerEvent().getData());
        } catch (Throwable exc) {
            event.getInnerEvent().setTypeEvent(Event.EVENT_FAILED_OPERATION);
            logger.error("doSagaOperation failed: Cause ", exc);
        }
    }

    @Override
    public void doSagaCompensation(Event<?> event) {
        try {
            this.delete((Diploma) event.getInnerEvent().getData());
        } catch (NotExistException notExistException) {
            logger.error("doSagaCompensation failed: Cause ", notExistException);
            event.getInnerEvent().setTypeEvent(Event.EVENT_FAILED_OPERATION);
        }
    }


}
