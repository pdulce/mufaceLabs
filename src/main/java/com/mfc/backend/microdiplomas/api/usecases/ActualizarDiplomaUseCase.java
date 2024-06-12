package com.mfc.backend.microdiplomas.api.usecases;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.service.DiplomaServicePort;
import org.springframework.beans.factory.annotation.Autowired;

public class ActualizarDiplomaUseCase {

    @Autowired
    DiplomaServicePort diplomaCommandServicePort;

    public Diploma ejecutar(Diploma diploma) {
        return diplomaCommandServicePort.update(diploma);
    }

}
