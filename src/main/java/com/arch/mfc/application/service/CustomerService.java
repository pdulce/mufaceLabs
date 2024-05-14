package com.arch.mfc.application.service;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.infra.domain.BaseEntity;
import com.arch.mfc.infra.inputport.GenericInputPort;
import com.arch.mfc.infra.outputport.CommandRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public abstract class CustomerService implements GenericInputPort {

    @Autowired
    CommandRepositoryInterface entityRepository;

    @Override
    public BaseEntity create(Map<String, Object> params) {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().timestamp());
        customer.setName((String) params.get("name"));
        customer.setCountry((String) params.get("country"));
        return entityRepository.save((BaseEntity) customer);
    }

    @Override
    public BaseEntity update(BaseEntity customer) {
        return entityRepository.save(customer);
    }

    @Override
    public BaseEntity getById(Long id) {
        return entityRepository.findById(id).get();
    }

}
