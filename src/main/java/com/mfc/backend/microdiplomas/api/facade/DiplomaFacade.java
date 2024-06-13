package com.mfc.backend.microdiplomas.api.facade;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.api.usecases.ActualizarDiplomaUseCase;
import com.mfc.backend.microdiplomas.api.usecases.BorrarTodosLosDiplomasUseCase;
import com.mfc.backend.microdiplomas.api.usecases.ConsultasDiplomasUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiplomaFacade {

    @Autowired
    private ActualizarDiplomaUseCase actualizarDiplomaUseCase;

    @Autowired
    private BorrarTodosLosDiplomasUseCase borrarTodosLosDiplomasUseCase;

    @Autowired
    private ConsultasDiplomasUseCase consultasDiplomasUseCase;

    public DiplomaDTO actualizarDiploma(DiplomaDTO diplomaDTO) {
        return this.actualizarDiplomaUseCase.ejecutar(diplomaDTO);
    }

    public void borrarTodosLosDiplomas() {

        this.borrarTodosLosDiplomasUseCase.ejecutar();
    }

    public List<DiplomaDTO> consultarTodosLosDiplomas() {
        return this.consultasDiplomasUseCase.ejecutar();
    }

    public List<DiplomaDTO> consultaDiplomasDeCliente(Long idCustomer) {
        return this.consultasDiplomasUseCase.ejecutar(idCustomer);
    }

    public List<DiplomaDTO> consultaDiplomasDeClientesConNombre(String name) {
        return this.consultasDiplomasUseCase.ejecutar(name);
    }


    public List<DiplomaDTO> consultaDiplomasDeRegionProvenza() {
        return this.consultasDiplomasUseCase.getDiplomasDeLaRegionProvenza();
    }


}
