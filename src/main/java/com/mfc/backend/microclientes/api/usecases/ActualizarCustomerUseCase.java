package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.backend.microclientes.domain.service.command.CustomerCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;

public class ActualizarCustomerUseCase {

    @Autowired
    CustomerCommandAdapterService customerCommandAdapterService;

    public Customer ejecutar(Customer customer) {
        return this.customerCommandAdapterService.update(customer);
    }

}
