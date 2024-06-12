package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.service.CustomerCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultasCustomerUseCase {

    @Autowired
    CustomerCommandAdapterService customerCommandAdapterService;

    public List<CustomerDTO> ejecutar(){
        return this.customerCommandAdapterService.consultarTodosLosClientes();
    }

    public CustomerDTO consultarPorIdCliente(Long id){
        return this.customerCommandAdapterService.consultarPorIdCliente(id);
    }

    public List<CustomerDTO> ejecutar(String name){
        return this.customerCommandAdapterService.buscarPorNombreCliente(name);
    }

    public List<CustomerDTO> dameListaCustomersDePaises(String prefixpais) {
        return this.customerCommandAdapterService.dameListaCustomersDePaises(prefixpais);
    }



}
