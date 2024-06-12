package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.repository.RegaloCommandRepository;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegaloAdapterService extends CommandServiceAdapter<Regalo, Long>
        implements RegaloServicePort {

    protected RegaloCommandRepository repository;

    @Autowired
    public RegaloAdapterService(RegaloCommandRepository regaloCommandRepository) {
        this.repository = regaloCommandRepository;
    }
    protected GenericRepositoryPort<Regalo, Long> getRepository() {
        return this.repository;
    }

}
