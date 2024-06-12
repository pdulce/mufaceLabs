package com.mfc.backend.microregalos.api.usecases;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.service.RegaloServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultasRegalosUseCase {

    @Autowired
    RegaloServicePort regaloServicePort;

    public List<RegaloDTO> ejecutar(Long customerId) {
        List<RegaloDTO> regalos = new ArrayList<>();
        this.regaloServicePort.buscarPorCampoValor("customerid", customerId).forEach((regalo -> {
            regalos.add(new RegaloDTO(regalo));
        }));
        return regalos;
    }

    public List<RegaloDTO> ejecutar() {
        List<RegaloDTO> regalos = new ArrayList<>();
        this.regaloServicePort.buscar().forEach((regalo -> {
            regalos.add(new RegaloDTO(regalo));
        }));
        return regalos;
    }

}
