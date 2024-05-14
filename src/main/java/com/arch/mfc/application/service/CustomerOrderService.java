package com.arch.mfc.application.service;

import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.infra.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderService extends BaseService {
    @Autowired
    JpaRepository<CustomerOrder, Long> entityRepository;

    @Override
    public JpaRepository getJPaRepository() {
        return entityRepository;
    }

}
