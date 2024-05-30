package com.arch.mfc.application.service.command;

import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.infra.events.adapter.CommandPublisher;
import com.arch.mfc.infra.outputadapter.relational.GenericJpaCommandService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandService extends GenericJpaCommandService<CustomerOrder> {

}
