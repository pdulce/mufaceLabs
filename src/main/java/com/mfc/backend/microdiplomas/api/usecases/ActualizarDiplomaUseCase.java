package com.mfc.backend.microdiplomas.api.usecases;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.service.DiplomaServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizarDiplomaUseCase {

    @Autowired
    DiplomaServicePort diplomaCommandServicePort;

    public DiplomaDTO ejecutar(DiplomaDTO diplomaDTO) {
        Diploma diploma = new Diploma(diplomaDTO);
        diploma = diplomaCommandServicePort.update(diploma);
        return new DiplomaDTO(diploma);
    }

}
