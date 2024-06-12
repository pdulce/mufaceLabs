package com.mfc.backend.microclientes.domain.service.command;

import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import com.mfc.backend.microclientes.domain.repository.command.CustomerOrderCommandRepositoryPort;
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