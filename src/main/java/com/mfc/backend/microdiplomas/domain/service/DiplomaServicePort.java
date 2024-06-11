package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.infra.output.port.CommandServicePort;

import java.util.List;

public interface DiplomaServicePort extends CommandServicePort<Diploma, Long> {

    public List<Diploma> getDiplomasDeLaRegionProvenza();

}
