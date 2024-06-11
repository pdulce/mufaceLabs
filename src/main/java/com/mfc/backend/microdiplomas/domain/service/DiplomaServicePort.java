package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.infra.output.port.CommandServicePort;

public interface DiplomaServicePort extends CommandServicePort<Diploma, Long> {

}
