package com.arch.mfc.infra.inputport;

import java.math.BigDecimal;

import com.arch.mfc.domain.Customer;
import com.arch.mfc.domain.Order;

public interface OrderInputPort {
    public Order createOrder(Customer customer, BigDecimal total );
}
