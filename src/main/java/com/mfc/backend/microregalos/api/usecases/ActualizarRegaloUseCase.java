package com.mfc.backend.microregalos.api.usecases;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.service.RegaloServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizarRegaloUseCase {

    @Autowired
    RegaloServicePort regaloServicePort;

    public Regalo ejecutar(Regalo regalo) {
        return regaloServicePort.update(regalo);
    }

}
