package com.mfc.backend.microdiplomas.api.command;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ActualizarDiplomaUseCase;
import org.springframework.beans.factory.annotation.Autowired;

public class ActualizarDiplomaCommand implements Command<DiplomaDTO> {

    @Autowired
    ActualizarDiplomaUseCase actualizarDiplomaUseCase;

    DiplomaDTO diplomaDTO;

    public DiplomaDTO getDiplomaDTO() {
        return diplomaDTO;
    }

    public ActualizarDiplomaCommand(DiplomaDTO diplomaDTO) {
        this.diplomaDTO = diplomaDTO;
    }

    @Override
    public DiplomaDTO execute() {
        return this.actualizarDiplomaUseCase.ejecutar(getDiplomaDTO());
    }

}
