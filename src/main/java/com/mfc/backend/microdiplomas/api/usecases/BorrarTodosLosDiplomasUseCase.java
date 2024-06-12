package com.mfc.backend.microdiplomas.api.usecases;

import com.mfc.backend.microdiplomas.domain.service.DiplomaServicePort;
import org.springframework.beans.factory.annotation.Autowired;

public class BorrarTodosLosDiplomasUseCase {

    @Autowired
    DiplomaServicePort diplomaCommandServicePort;

    public void ejecutar() {
        diplomaCommandServicePort.deleteAll();
    }

}
