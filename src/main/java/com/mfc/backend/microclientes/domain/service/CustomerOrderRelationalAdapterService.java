package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import com.mfc.backend.microclientes.domain.repository.CustomerOrderCommandRepositoryPort;
import com.mfc.infra.output.adapter.RelationalOperationsAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderRelationalAdapterService extends RelationalOperationsAdapter<CustomerOrder, Long>
        implements CustomerOrderServicePort{

    @Autowired
    CustomerOrderCommandRepositoryPort repository;

    protected GenericRepositoryPort<CustomerOrder, Long> getRepository() {
        return this.repository;
    }

    @Override
    public CustomerOrderDTO crearPedido(CustomerOrderDTO customerOrderDTO) {
        CustomerOrder order = this.crear(new CustomerOrder(customerOrderDTO));
        return new CustomerOrderDTO(order.getId(), order.getCustomer().getId(), order.getCustomer().getName(),
                order.getTotal());
    }

}
