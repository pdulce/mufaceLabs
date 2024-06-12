package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.infra.output.port.CommandServicePort;

import java.util.ArrayList;
import java.util.List;

public interface RegaloServicePort extends CommandServicePort<Regalo, Long> {

    RegaloDTO actualizarRegalo(RegaloDTO regaloDTO);

    List<RegaloDTO> consultarRegalosDeCustomer(Long customerId);

    List<RegaloDTO> consultarTodosLosRegalodEntregados();

}
