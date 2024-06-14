package com.mfc.backend.microdiplomas.api.usecases;

import com.mfc.backend.microdiplomas.domain.service.DiplomaServicePort;
import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActualizarDiplomaUseCase {

    @Autowired
    DiplomaServicePort diplomaCommandServicePort;

    public DiplomaDTO ejecutar(DiplomaDTO diplomaDTO) {
        DiplomaDTO diplomaDTOSaved = this.diplomaCommandServicePort.actualizar(diplomaDTO);
        this.diplomaCommandServicePort.setContinente(diplomaDTOSaved);
        return diplomaDTOSaved;
    }

}
