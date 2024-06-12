package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import com.mfc.backend.microclientes.domain.service.CustomerOrderCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrearPedidoUseCase {

    @Autowired
    CustomerOrderCommandAdapterService customerCommandAdapterService;

    public CustomerOrderDTO ejecutar(CustomerOrderDTO customerOrderDTO) {
        return new CustomerOrderDTO(this.customerCommandAdapterService.crear(new CustomerOrder(customerOrderDTO)));
    }



}
