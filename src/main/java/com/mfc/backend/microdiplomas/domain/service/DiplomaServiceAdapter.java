package com.mfc.backend.microdiplomas.domain.service;

import com.mfc.backend.microdiplomas.domain.model.Diploma;
import com.mfc.backend.microdiplomas.domain.repository.DiplomaCommandRepository;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiplomaServiceAdapter extends CommandServiceAdapter<Diploma, Long> {

    protected DiplomaCommandRepository repository;

    @Autowired
    public DiplomaServiceAdapter(DiplomaCommandRepository diplomaCommandRepository) {
        this.repository = diplomaCommandRepository;
    }
    protected GenericRepositoryPort<Diploma, Long> getRepository() {
        return this.repository;
    }

}
