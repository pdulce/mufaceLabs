package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.output.port.ArqRelationalServicePort;

public interface RegaloServicePortArq extends ArqRelationalServicePort<Regalo, RegaloDTO, Long> {


}
