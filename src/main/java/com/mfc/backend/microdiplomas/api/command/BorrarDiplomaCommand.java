package com.mfc.backend.microdiplomas.api.command;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.BorrarTodosLosDiplomasUseCase;

public class BorrarDiplomaCommand implements Command<DiplomaDTO> {

    private final BorrarTodosLosDiplomasUseCase borrarTodosLosDiplomasUseCase;

    DiplomaDTO diplomaDTO;

    public DiplomaDTO getDiplomaDTO() {
        return diplomaDTO;
    }

    public BorrarDiplomaCommand(BorrarTodosLosDiplomasUseCase borrarTodosLosDiplomasUseCase, DiplomaDTO diplomaDTO) {
        this.borrarTodosLosDiplomasUseCase = borrarTodosLosDiplomasUseCase;
        this.diplomaDTO = diplomaDTO;
    }

    @Override
    public DiplomaDTO execute() {
        this.borrarTodosLosDiplomasUseCase.ejecutar();
        return null;
    }

}
