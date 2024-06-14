package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.output.port.ArqServicePort;

public interface RegaloServicePort extends ArqServicePort<Regalo, RegaloDTO, Long> {


}
