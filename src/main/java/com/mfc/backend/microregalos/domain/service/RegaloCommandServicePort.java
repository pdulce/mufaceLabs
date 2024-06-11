package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.output.port.CommandServicePort;

public interface RegaloCommandServicePort extends CommandServicePort<Regalo, Long> {

}
