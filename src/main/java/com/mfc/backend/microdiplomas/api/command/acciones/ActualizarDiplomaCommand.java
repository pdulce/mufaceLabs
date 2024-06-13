package com.mfc.backend.microdiplomas.api.command.acciones;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ActualizarDiplomaUseCase;

public class ActualizarDiplomaCommand implements Command<DiplomaDTO> {

    private final ActualizarDiplomaUseCase actualizarDiplomaUseCase;

    DiplomaDTO diplomaDTO;

    public DiplomaDTO getDiplomaDTO() {
        return diplomaDTO;
    }

    public ActualizarDiplomaCommand(ActualizarDiplomaUseCase actualizarDiplomaUseCase, DiplomaDTO diplomaDTO) {
        this.actualizarDiplomaUseCase = actualizarDiplomaUseCase;
        this.diplomaDTO = diplomaDTO;
    }

    @Override
    public DiplomaDTO execute() {
        return this.actualizarDiplomaUseCase.ejecutar(getDiplomaDTO());
    }

}
