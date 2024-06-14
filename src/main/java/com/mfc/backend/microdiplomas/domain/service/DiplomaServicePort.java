package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.infra.output.port.ArqRelationalServicePort;

import java.util.List;

public interface DiplomaServicePort extends ArqRelationalServicePort<Diploma, DiplomaDTO, Long> {

    List<DiplomaDTO> getDiplomasDeLaRegionProvenza();


}
