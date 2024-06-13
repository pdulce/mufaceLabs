package com.mfc.backend.microdiplomas.api.command;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ConsultasDiplomasUseCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BuscarDiplomasDeClientesConNombre implements Command<List<DiplomaDTO>> {

    @Autowired
    ConsultasDiplomasUseCase consultasDiplomasUseCase;

    DiplomaDTO diplomaDTO;

    public DiplomaDTO getDiplomaDTO() {
        return diplomaDTO;
    }

    public BuscarDiplomasDeClientesConNombre(DiplomaDTO diplomaDTO) {
        this.diplomaDTO = diplomaDTO;
    }

    @Override
    public List<DiplomaDTO> execute() {
        return this.consultasDiplomasUseCase.consultarDiplomasPorNombreClientes(getDiplomaDTO().getName());
    }
}
