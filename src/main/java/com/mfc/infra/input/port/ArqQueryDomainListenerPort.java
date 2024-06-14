package com.mfc.infra.input.port;

import com.mfc.infra.dto.IArqDTO;
import com.mfc.infra.event.ArqEvent;

public interface ArqQueryDomainListenerPort<T, D extends IArqDTO, ID> {

    void listen(ArqEvent<?> eventArch);

    void procesarEvento(ArqEvent<?> event);


}
