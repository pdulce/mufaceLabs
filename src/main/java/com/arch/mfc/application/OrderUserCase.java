package com.arch.mfc.application;

import java.math.BigDecimal;
import java.util.UUID;

import com.arch.mfc.domain.Customer;
import com.arch.mfc.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arch.mfc.infra.inputport.OrderInputPort;
import com.arch.mfc.infra.outputport.CommandRepository;

@Component
public class OrderUserCase implements OrderInputPort {

    @Autowired
    CommandRepository entityRepository;

    @Override
    public Order createOrder(Customer customer, BigDecimal total) {
        Order order = new Order();
        order.setId(Long.valueOf(UUID.randomUUID().toString()));
        order.setCustomer(customer);
        order.setTotal(total);
        return entityRepository.save( order );
    }
    
}
