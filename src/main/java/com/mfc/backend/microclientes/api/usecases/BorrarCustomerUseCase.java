package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.service.CustomerRelationalAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrarCustomerUseCase {

    @Autowired
    CustomerRelationalAdapterService customerCommandAdapterService;

    public void ejecutar(CustomerDTO customerDTO) {
        this.customerCommandAdapterService.borrarCliente(customerDTO);
    }

    public void ejecutar() {
        this.customerCommandAdapterService.borrarTodosLosClientes();
    }

}
