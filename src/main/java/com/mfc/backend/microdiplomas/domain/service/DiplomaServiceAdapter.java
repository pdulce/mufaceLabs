package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.api.dto.DiplomaDTO;
import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.repository.DiplomaCommandRepository;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiplomaServiceAdapter extends CommandServiceAdapter<Diploma, Long> implements DiplomaServicePort {

    @Autowired
    private DiplomaCommandRepository repository;

    protected GenericRepositoryPort<Diploma, Long> getRepository() {
        return this.repository;
    }

    @Override
    public List<DiplomaDTO> getDiplomasDeLaRegionProvenza() {
        List<DiplomaDTO> diplomaDTOS = new ArrayList<>();
        Diploma dFilter = new Diploma();
        dFilter.setRegion("Provenza");
        Example<Diploma> example = Example.of(dFilter);
        repository.findAll(example).forEach((diploma -> {
            diplomaDTOS.add(new DiplomaDTO(diploma.getId(), diploma.getIdcustomer(), diploma.getName(), diploma.getTitulo(),
                    diploma.getRegion()));
        }));
        return diplomaDTOS;
    }

    @Override
    public DiplomaDTO actualizarDiploma(DiplomaDTO diplomaDTO) {
        Diploma diploma = new Diploma(diplomaDTO);
        diploma = this.actualizar(diploma);
        return new DiplomaDTO(diploma.getId(), diploma.getIdcustomer(), diploma.getName(), diploma.getTitulo(),
                diploma.getRegion());
    }

    @Override
    public void borrarDiplomas() {

    }

    public List<DiplomaDTO> buscarDiplomasDeCustomer(Long customerId) {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.buscarPorCampoValor("idcustomer", customerId).forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma.getId(), diploma.getIdcustomer(), diploma.getName(), diploma.getTitulo(),
                    diploma.getRegion()));
        }));
        return diplomas;
    }

    public List<DiplomaDTO> buscarDiplomasPorNombreCustomer(String name) {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.buscarPorCampoValor("name", name).forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma.getId(), diploma.getIdcustomer(), diploma.getName(), diploma.getTitulo(),
                    diploma.getRegion()));
        }));
        return diplomas;
    }

    public List<DiplomaDTO> buscarTodosLosDiplomas() {
        List<DiplomaDTO> diplomas = new ArrayList<>();
        this.buscar().forEach((diploma -> {
            diplomas.add(new DiplomaDTO(diploma.getId(), diploma.getIdcustomer(), diploma.getName(), diploma.getTitulo(),
                    diploma.getRegion()));
        }));
        return diplomas;
    }


}
