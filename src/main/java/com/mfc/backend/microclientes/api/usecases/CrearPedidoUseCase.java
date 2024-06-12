package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import com.mfc.backend.microclientes.domain.service.CustomerOrderCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrearPedidoUseCase {

    @Autowired
    CustomerOrderCommandAdapterService customerCommandAdapterService;


    public CustomerOrder ejecutar(CustomerOrder customerOrder) {
        return this.customerCommandAdapterService.crear(customerOrder);
    }



}
