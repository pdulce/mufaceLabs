package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.service.CustomerArqRelationalServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultasCustomerUseCase {

    @Autowired
    CustomerArqRelationalServicePort customerCommandServicePort;

    public List<CustomerDTO> ejecutar(){
        return this.customerCommandServicePort.buscarTodos();
    }

    public CustomerDTO ejecutar(Long id){
        return this.customerCommandServicePort.buscarPorId(id);
    }

    public List<CustomerDTO> ejecutar(String name){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(name);
        return this.customerCommandServicePort.buscarCoincidenciasNoEstricto(customerDTO);
    }

    public List<CustomerDTO> dameListaCustomersDePaises(String prefixpais) {
        return this.customerCommandServicePort.dameListaCustomersDePaises(prefixpais);
    }



}
