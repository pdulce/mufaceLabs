package com.arch.mfc.infra.inputport;

import java.util.List;

import com.arch.mfc.domain.Customer;

public interface CustomerInputPort {

    public Customer createCustomer(String name, String country);

    public Customer getById(Long customerId);

    public List<Customer> getAll();
    
}
