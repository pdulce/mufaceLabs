package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.infra.output.port.RelationalServicePort;

import java.util.List;

public interface DiplomaServicePort extends RelationalServicePort<Diploma, Long> {

    List<DiplomaDTO> getDiplomasDeLaRegionProvenza();

    DiplomaDTO actualizarDiploma(DiplomaDTO diplomaDTO);

    void borrarDiplomas();


    List<DiplomaDTO> buscarDiplomasDeCustomer(Long id);


    List<DiplomaDTO> buscarDiplomasPorNombreCustomer(String name);

    List<DiplomaDTO> buscarTodosLosDiplomas();

}
