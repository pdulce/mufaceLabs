package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.backend.microclientes.domain.service.command.CustomerCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrarCustomerUseCase {

    @Autowired
    CustomerCommandAdapterService customerCommandAdapterService;

    public void ejecutar(Customer customer) {
        this.customerCommandAdapterService.delete(customer);
    }

    public void ejecutar() {
        this.customerCommandAdapterService.deleteAll();
    }

}
