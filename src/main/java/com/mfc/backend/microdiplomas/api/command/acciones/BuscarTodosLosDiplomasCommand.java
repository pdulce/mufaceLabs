package com.mfc.backend.microdiplomas.api.command.acciones;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ConsultasDiplomasUseCase;

import java.util.List;

public class BuscarTodosLosDiplomasCommand implements Command<List<DiplomaDTO>> {

    private final ConsultasDiplomasUseCase consultasDiplomasUseCase;

    DiplomaDTO diplomaDTO;

    public DiplomaDTO getDiplomaDTO() {
        return diplomaDTO;
    }

    public BuscarTodosLosDiplomasCommand(ConsultasDiplomasUseCase consultasDiplomasUseCase, DiplomaDTO diplomaDTO) {
        this.consultasDiplomasUseCase = consultasDiplomasUseCase;
        this.diplomaDTO = diplomaDTO;
    }

    @Override
    public List<DiplomaDTO> execute() {
        return this.consultasDiplomasUseCase.consultarTodos();
    }

}
