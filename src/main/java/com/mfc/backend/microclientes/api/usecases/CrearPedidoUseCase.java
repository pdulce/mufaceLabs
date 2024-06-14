package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.domain.service.CustomerOrderServicePortArq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrearPedidoUseCase {

    @Autowired
    CustomerOrderServicePortArq customerOrderServicePort;

    public CustomerOrderDTO ejecutar(CustomerOrderDTO customerOrderDTO) {
        return this.customerOrderServicePort.crear(customerOrderDTO);
    }


}
