package com.arch.mfc.application.service;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.infra.domain.BaseEntity;
import com.arch.mfc.infra.inputport.GenericInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomerService implements GenericInputPort {

    @Autowired
    JpaRepository<BaseEntity, Long> entityRepository;

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

    @Override
    public List<BaseEntity> getAll() {
        return entityRepository.findAll();
    }

}
