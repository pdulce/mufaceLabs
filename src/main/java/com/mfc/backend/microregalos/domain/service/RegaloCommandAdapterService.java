package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.repository.RegaloCommandRepository;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegaloCommandAdapterService extends CommandServiceAdapter<Regalo, Long> {

    protected RegaloCommandRepository repository;

    @Autowired
    public RegaloCommandAdapterService(RegaloCommandRepository regaloCommandRepository) {
        this.repository = regaloCommandRepository;
    }
    protected GenericRepositoryPort<Regalo, Long> getRepository() {
        return this.repository;
    }

}
