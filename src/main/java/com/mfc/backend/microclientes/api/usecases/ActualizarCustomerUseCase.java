package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.service.CustomerArqRelationalServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActualizarCustomerUseCase {

    @Autowired
    CustomerArqRelationalServicePort customerCommandServicePort;

    public CustomerDTO ejecutar(CustomerDTO customerDTO) {
        return this.customerCommandServicePort.actualizar(customerDTO);
    }

}
