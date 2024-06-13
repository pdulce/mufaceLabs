package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.output.port.RelationalServicePort;

import java.util.List;

public interface RegaloServicePort extends RelationalServicePort<Regalo, Long> {

    RegaloDTO actualizarRegalo(RegaloDTO regaloDTO);

    List<RegaloDTO> consultarRegalosDeCustomer(Long customerId);

    List<RegaloDTO> consultarTodosLosRegalodEntregados();

}
