package com.mfc.backend.microdiplomas.api.usecases;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.domain.service.DiplomaServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultasDiplomasUseCase {

    @Autowired
    DiplomaServicePort diplomaCommandServicePort;

    public List<DiplomaDTO> ejecutar(Long customerId) {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.diplomaCommandServicePort.buscarPorCampoValor("idcustomer", customerId).forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }

    public List<DiplomaDTO> ejecutar(String name) {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.diplomaCommandServicePort.buscarPorCampoValor("name", name).forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }

    public List<DiplomaDTO> ejecutar() {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.diplomaCommandServicePort.buscar().forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }

    public List<DiplomaDTO> getDiplomasDeLaRegionProvenza() {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.diplomaCommandServicePort.getDiplomasDeLaRegionProvenza().forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma));
        }));
        return diplomas;
    }


}
