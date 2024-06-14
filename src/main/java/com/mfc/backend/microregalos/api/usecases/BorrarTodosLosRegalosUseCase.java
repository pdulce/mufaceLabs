package com.mfc.backend.microregalos.api.usecases;

import com.mfc.backend.microregalos.domain.service.RegaloServicePortArq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrarTodosLosRegalosUseCase {

    @Autowired
    RegaloServicePortArq regaloServicePort;

    public void ejecutar() {
        regaloServicePort.borrar();
    }

}
