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
        List<CustomerDTO> customers = new ArrayList<>();
        this.customerCommandAdapterService.buscar().forEach((customer -> {
            customers.add(new CustomerDTO(customer));
        }));
        return customers;
    }

    public CustomerDTO ejecutar(Long id){

        return new CustomerDTO(this.customerCommandAdapterService.buscarPorId(id));
    }

    public List<CustomerDTO> ejecutar(String name){
        List<CustomerDTO> customers = new ArrayList<>();
        this.customerCommandAdapterService.buscarPorCampoValor("name", name).forEach((customer -> {
            customers.add(new CustomerDTO(customer));
        }));
        return customers;
    }

    public List<CustomerDTO> dameListaCustomersDePaises(String prefixpais){
        List<CustomerDTO> customers = new ArrayList<>();
        this.customerCommandAdapterService.dameListaCustomersDePaises(prefixpais).forEach((customer -> {
            customers.add(new CustomerDTO(customer));
        }));
        return customers;
    }



}
