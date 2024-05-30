package com.arch.mfc.application.service.command;

import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.infra.outputadapter.GenericJpaCommandService;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandService extends GenericJpaCommandService<CustomerOrder> {

}
