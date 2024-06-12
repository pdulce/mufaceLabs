package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.service.CustomerCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrarCustomerUseCase {

    @Autowired
    CustomerCommandAdapterService customerCommandAdapterService;

    public void ejecutar(CustomerDTO customerDTO) {
        this.customerCommandAdapterService.borrarCliente(customerDTO);
    }

    public void ejecutar() {
        this.customerCommandAdapterService.borrarTodosLosClientes();
    }

}
