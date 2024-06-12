package com.mfc.backend.microclientes.domain.repository;


import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderCommandRepositoryPort extends GenericRepositoryPort<CustomerOrder, Long> {

}
