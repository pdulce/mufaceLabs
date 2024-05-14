package com.arch.mfc.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.domain.Order;
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
    public Entity create(Map<String, Object> params) {
        Order order = new Order();
        order.setId(UUID.randomUUID().timestamp());
        order.setCustomer((Customer) params.get("customer"));
        order.setTotal((BigDecimal) params.get("total"));
        return entityRepository.save((Entity) order);
    }

    @Override
    public Entity update(Entity order) {
        return entityRepository.save(order);
    }

    @Override
    public Entity getById(Long id) {
        return entityRepository.findById(id).get();
    }

    @Override
    public List<Entity> getAll() {
        return entityRepository.findAll();
    }

}
