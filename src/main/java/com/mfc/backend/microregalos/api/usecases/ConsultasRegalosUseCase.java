package com.mfc.backend.microregalos.api.usecases;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.service.RegaloServicePortArq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultasRegalosUseCase {

    @Autowired
    RegaloServicePortArq regaloServicePort;

    public List<RegaloDTO> ejecutar(Long customerId) {
        return this.regaloServicePort.buscarPorCampoValor("customerId", customerId);
    }

    public List<RegaloDTO> ejecutar() {
        return this.regaloServicePort.buscarTodos();
    }

}
