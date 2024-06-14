package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.repository.CustomerCommandRepositoryPort;
import com.mfc.infra.output.adapter.ArqRelationalServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerArqArqRelationalAdapterService extends ArqRelationalServiceAdapter<Customer, CustomerDTO, Long>
        implements CustomerArqRelationalServicePort {

    @Autowired
    CustomerCommandRepositoryPort repository;

    protected JpaRepository<Customer, Long> getRepository() {
        return this.repository;
    }

    public List<CustomerDTO> dameListaCustomersDePaises(String prefixpais){
        List<CustomerDTO> customers = new ArrayList<>();
        repository.findAllByCountryContains(prefixpais).forEach((customer -> {
            customers.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getCountry()));
        }));
        return customers;
    }




}
