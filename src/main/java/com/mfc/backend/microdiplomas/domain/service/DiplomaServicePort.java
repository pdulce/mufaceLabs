package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.infra.output.port.ArqServicePort;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;

import java.util.List;

public interface DiplomaServicePort extends ArqServicePort<Diploma, DiplomaDTO, Long> {

    List<DiplomaDTO> getDiplomasDeLaRegionProvenza();

    void setContinente(DiplomaDTO diplomaDTO);


}
