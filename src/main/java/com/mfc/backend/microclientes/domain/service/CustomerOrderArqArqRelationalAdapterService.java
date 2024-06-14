package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import com.mfc.backend.microclientes.domain.repository.CustomerOrderCommandRepositoryPort;
import com.mfc.infra.output.adapter.ArqRelationalServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderArqArqRelationalAdapterService extends ArqRelationalServiceAdapter<CustomerOrder, CustomerOrderDTO, Long>
        implements CustomerOrderServicePortArq {

    @Autowired
    CustomerOrderCommandRepositoryPort repository;

    protected JpaRepository<CustomerOrder, Long> getRepository() {
        return this.repository;
    }


}
