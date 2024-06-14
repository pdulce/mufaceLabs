package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.infra.output.port.ArqRelationalServicePort;
import com.mfc.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.microdiplomas.domain.model.Diploma;

import java.util.List;

public interface DiplomaServicePort extends ArqRelationalServicePort<Diploma, DiplomaDTO, Long> {

    List<DiplomaDTO> getDiplomasDeLaRegionProvenza();

    void setContinente(DiplomaDTO diplomaDTO);


}
