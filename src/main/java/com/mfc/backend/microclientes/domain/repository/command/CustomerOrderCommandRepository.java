package com.mfc.backend.microclientes.domain.repository.command;


import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import com.mfc.infra.output.port.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderCommandRepository extends GenericRepository<CustomerOrder, Long> {

}
