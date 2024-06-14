package com.mfc.backend.microregalos.api.usecases;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.service.RegaloServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultasRegalosUseCase {

    @Autowired
    RegaloServicePort regaloServicePort;

    public List<RegaloDTO> ejecutar(Long customerId) {
        RegaloDTO regaloDTO = new RegaloDTO();
        regaloDTO.setCustomerid(customerId);
        return this.regaloServicePort.buscarCoincidenciasEstricto(regaloDTO);
    }

    public List<RegaloDTO> ejecutar() {
        return this.regaloServicePort.buscarTodos();
    }

}
