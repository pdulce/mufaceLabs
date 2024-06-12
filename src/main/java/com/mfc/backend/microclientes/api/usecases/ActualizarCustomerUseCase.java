package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.service.CustomerCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizarCustomerUseCase {

    @Autowired
    CustomerCommandAdapterService customerCommandAdapterService;

    public CustomerDTO ejecutar(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO);
        customer = this.customerCommandAdapterService.actualizar(customer);
        return new CustomerDTO(customer);
    }

}
