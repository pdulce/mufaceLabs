package com.mfc.backend.microdiplomas.api.command;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.BorrarTodosLosDiplomasUseCase;
import org.springframework.beans.factory.annotation.Autowired;

public class BorrarDiplomaCommand implements Command<DiplomaDTO> {

    @Autowired
    BorrarTodosLosDiplomasUseCase borrarTodosLosDiplomasUseCase;

    DiplomaDTO diplomaDTO;

    public DiplomaDTO getDiplomaDTO() {
        return diplomaDTO;
    }

    public BorrarDiplomaCommand(DiplomaDTO diplomaDTO) {
        this.diplomaDTO = diplomaDTO;
    }

    @Override
    public DiplomaDTO execute() {
        this.borrarTodosLosDiplomasUseCase.ejecutar();
        return null;
    }

}
