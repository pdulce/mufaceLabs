package com.arch.mfc.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.infra.domain.BaseEntity;
import com.arch.mfc.infra.outputport.CommandRepositoryInterface;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import com.arch.mfc.infra.inputport.GenericInputPort;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements GenericInputPort {

    @Autowired
    CommandRepositoryInterface entityRepository;

    @Override
    public BaseEntity create(Map<String, Object> params) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(UUID.randomUUID().timestamp());
        customerOrder.setCustomer((Customer) params.get("customer"));
        customerOrder.setTotal((BigDecimal) params.get("total"));
        return entityRepository.save((BaseEntity) customerOrder);
    }

    @Override
    public BaseEntity update(BaseEntity order) {
        return entityRepository.save(order);
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
