package com.mfc.backend.microdiplomas.api.usecases;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.service.DiplomaServicePort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ConsultasDiplomasUseCase {

    @Autowired
    DiplomaServicePort diplomaCommandServicePort;

    public List<Diploma> ejecutar(Long customerId) {
        return diplomaCommandServicePort.findAllByFieldvalue("idcustomer", customerId);
    }

    public List<Diploma> ejecutar() {
        return diplomaCommandServicePort.findAll();
    }

}
