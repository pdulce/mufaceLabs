package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.repository.RegaloCommandRepository;
import com.mfc.infra.output.adapter.RelationalServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegaloAdapterService extends RelationalServiceAdapter<Regalo, RegaloDTO, Long>
        implements RegaloServicePort {
    @Autowired
    private RegaloCommandRepository repository;

    protected GenericRepositoryPort<Regalo, Long> getRepository() {
        return this.repository;
    }


}
