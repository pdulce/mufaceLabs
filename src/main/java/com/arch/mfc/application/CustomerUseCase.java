package com.arch.mfc.application;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arch.mfc.domain.Customer;
import com.arch.mfc.infra.inputport.CustomerInputPort;
import com.arch.mfc.infra.outputport.CommandRepository;

@Component
public class CustomerUseCase implements CustomerInputPort {

    @Autowired
    CommandRepository entityRepository;

    @Override
    public Customer createCustomer(String name, String country) {
        Customer customer = new Customer();
        customer.setId(Long.valueOf(UUID.randomUUID().toString()));
        customer.setName(name);
        customer.setCountry(country);
        return entityRepository.save( customer );
    }

    @Override
    public Customer getById(Long customerId) {
        return entityRepository.getById(customerId, Customer.class);
    }

    @Override
    public List<Customer> getAll() {
        return entityRepository.getAll( Customer.class );
    }
    
}
