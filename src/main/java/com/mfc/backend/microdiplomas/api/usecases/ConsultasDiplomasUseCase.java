package com.mfc.backend.microdiplomas.api.usecases;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.domain.service.DiplomaServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultasDiplomasUseCase {

    @Autowired
    DiplomaServicePort diplomaCommandServicePort;

    public List<DiplomaDTO> ejecutar(Long customerId) {
        return this.diplomaCommandServicePort.buscarDiplomasDeCustomer(customerId);
    }

    public List<DiplomaDTO> ejecutar(String name) {
        return this.diplomaCommandServicePort.buscarDiplomasPorNombreCustomer(name);
    }

    public List<DiplomaDTO> ejecutar() {
        return this.diplomaCommandServicePort.buscarTodosLosDiplomas();
    }

    public List<DiplomaDTO> getDiplomasDeLaRegionProvenza() {
        return this.diplomaCommandServicePort.getDiplomasDeLaRegionProvenza();
    }


}
