package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import com.mfc.backend.microclientes.domain.repository.CustomerOrderCommandRepositoryPort;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandAdapterService extends CommandServiceAdapter<CustomerOrder, Long> {

    protected CustomerOrderCommandRepositoryPort repository;

    @Autowired
    public CustomerOrderCommandAdapterService(CustomerOrderCommandRepositoryPort customerOrderCommandRepositoryPortP) {
        this.repository = customerOrderCommandRepositoryPortP;
    }
    protected GenericRepositoryPort<CustomerOrder, Long> getRepository() {
        return this.repository;
    }

}
