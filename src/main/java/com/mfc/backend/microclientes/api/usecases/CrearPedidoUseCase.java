package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import com.mfc.backend.microclientes.domain.service.command.CustomerOrderCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrearPedidoUseCase {

    @Autowired
    CustomerOrderCommandAdapterService customerCommandAdapterService;


    public CustomerOrder ejecutar(CustomerOrder customerOrder) {
        return this.customerCommandAdapterService.insert(customerOrder);
    }



}
