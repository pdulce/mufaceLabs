package com.mfc.backend.microregalos.domain.service;

import com.mfc.backend.microregalos.api.dto.RegaloDTO;
import com.mfc.backend.microregalos.domain.model.Regalo;
import com.mfc.backend.microregalos.domain.repository.RegaloCommandRepository;
import com.mfc.infra.output.adapter.ArqRelationalServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RegaloAdapterService extends ArqRelationalServiceAdapter<Regalo, RegaloDTO, Long>
        implements RegaloServicePort {
    @Autowired
    private RegaloCommandRepository repository;

    protected JpaRepository<Regalo, Long> getRepository() {
        return this.repository;
    }


}
