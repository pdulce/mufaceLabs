package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.repository.DiplomaCommandRepository;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiplomaServiceAdapter extends CommandServiceAdapter<Diploma, Long> implements DiplomaServicePort {

    @Autowired
    private DiplomaCommandRepository repository;

    protected GenericRepositoryPort<Diploma, Long> getRepository() {
        return this.repository;
    }

    @Override
    public List<Diploma> getDiplomasDeLaRegionProvenza() {
        Diploma dFilter = new Diploma();
        dFilter.setRegion("Provenza");
        Example<Diploma> example = Example.of(dFilter);
        return repository.findAll(example);
    }

}
