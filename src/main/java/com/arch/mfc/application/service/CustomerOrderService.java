package com.arch.mfc.application.service;

import com.arch.mfc.application.domain.command.Customer;
import com.arch.mfc.application.domain.command.CustomerOrder;
import com.arch.mfc.infra.outputadapter.relational.GenericJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService extends GenericJpaService<CustomerOrder> {

    public CustomerOrderService() {}
    public CustomerOrderService(Class<CustomerOrder> entityClass) {
        super(entityClass);
    }

}
