package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.service.CustomerCommandServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultasCustomerUseCase {

    @Autowired
    CustomerCommandServicePort customerCommandServicePort;

    public List<CustomerDTO> ejecutar(){
        return this.customerCommandServicePort.consultarTodosLosClientes();
    }

    public CustomerDTO ejecutar(Long id){
        return this.customerCommandServicePort.consultarPorIdCliente(id);
    }

    public List<CustomerDTO> ejecutar(String name){
        return this.customerCommandServicePort.buscarPorNombreCliente(name);
    }

    public List<CustomerDTO> dameListaCustomersDePaises(String prefixpais) {
        return this.customerCommandServicePort.dameListaCustomersDePaises(prefixpais);
    }



}
