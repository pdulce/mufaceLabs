package com.arch.mfc.application.service;

import com.arch.mfc.application.domain.command.CustomerOrder;
import com.arch.mfc.infra.outputadapter.relational.GenericJpaCommandService;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandCommandService extends GenericJpaCommandService<CustomerOrder> {

    public CustomerOrderCommandCommandService() {}
    public CustomerOrderCommandCommandService(Class<CustomerOrder> entityClass) {
        super(entityClass);
    }

}
