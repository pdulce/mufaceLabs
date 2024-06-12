package com.mfc.backend.microregalos.api.usecases;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.service.RegaloServicePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ConsultasRegalosUseCase {

    @Autowired
    RegaloServicePort regaloServicePort;

    public List<Regalo> ejecutar(Long customerId) {
        return regaloServicePort.findAllByFieldvalue("customerid", customerId);
    }

    public List<Regalo> ejecutar() {
        return regaloServicePort.findAll();
    }

}
