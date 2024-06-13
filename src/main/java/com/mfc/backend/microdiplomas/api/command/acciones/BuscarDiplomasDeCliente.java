package com.mfc.backend.microdiplomas.api.command.acciones;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ConsultasDiplomasUseCase;

import java.util.List;

public class BuscarDiplomasDeCliente implements Command<List<DiplomaDTO>> {

    private final ConsultasDiplomasUseCase consultasDiplomasUseCase;

    DiplomaDTO diplomaDTO;

    public DiplomaDTO getDiplomaDTO() {
        return diplomaDTO;
    }

    public BuscarDiplomasDeCliente(ConsultasDiplomasUseCase consultasDiplomasUseCase, DiplomaDTO diplomaDTO) {
        this.consultasDiplomasUseCase = consultasDiplomasUseCase;
        this.diplomaDTO = diplomaDTO;
    }

    @Override
    public List<DiplomaDTO> execute() {
        return this.consultasDiplomasUseCase.consultarDiplomasDeCliente(getDiplomaDTO().getIdcustomer());
    }
}
