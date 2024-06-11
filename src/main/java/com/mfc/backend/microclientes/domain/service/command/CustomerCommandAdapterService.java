package com.mfc.backend.microclientes.domain.service.command;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.backend.microclientes.domain.repository.command.CustomerCommandRepositoryPort;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCommandAdapterService extends CommandServiceAdapter<Customer, Long>
        implements CustomerCommandServicePort {

    protected CustomerCommandRepositoryPort repository;

    @Autowired
    public CustomerCommandAdapterService(CustomerCommandRepositoryPort customerCommandRepositoryPortP) {
        this.repository = customerCommandRepositoryPortP;
    }

    protected GenericRepositoryPort<Customer, Long> getRepository() {
        return this.repository;
    }

    //
    public List<Customer> dameListaCustomersDePaises (String paisPrefix) {
        return this.repository.findAllByCountryContains(paisPrefix);
    }



}
