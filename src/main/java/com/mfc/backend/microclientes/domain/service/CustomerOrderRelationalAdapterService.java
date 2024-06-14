package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import com.mfc.backend.microclientes.domain.repository.CustomerOrderCommandRepositoryPort;
import com.mfc.infra.output.adapter.RelationalServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderRelationalAdapterService extends RelationalServiceAdapter<CustomerOrder, CustomerOrderDTO, Long>
        implements CustomerOrderServicePort{

    @Autowired
    CustomerOrderCommandRepositoryPort repository;

    protected GenericRepositoryPort<CustomerOrder, Long> getRepository() {
        return this.repository;
    }


}
