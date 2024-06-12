package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.backend.microclientes.domain.service.command.CustomerCommandAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultasCustomerUseCase {

    @Autowired
    CustomerCommandAdapterService customerCommandAdapterService;

    public List<Customer> ejecutar(){
        return this.customerCommandAdapterService.findAll();
    }

    public Customer ejecutar(Long id){
        return this.customerCommandAdapterService.findById(id);
    }

    public List<Customer> ejecutar(String name){
        return this.customerCommandAdapterService.findAllByFieldvalue("name", name);
    }

    public List<Customer> dameListaCustomersDePaises(String prefixpais){
        return this.customerCommandAdapterService.dameListaCustomersDePaises(prefixpais);
    }



}
