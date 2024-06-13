package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.backend.microclientes.domain.repository.CustomerCommandRepositoryPort;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import com.mfc.infra.output.port.GenericRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerCommandAdapterService extends CommandServiceAdapter<Customer, Long>
        implements CustomerCommandServicePort {

    @Autowired
    CustomerCommandRepositoryPort repository;

    protected GenericRepositoryPort<Customer, Long> getRepository() {
        return this.repository;
    }

    //
    public CustomerDTO actualizarCliente(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO);
        customer = this.actualizar(customer);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getCountry());
    }

    public void borrarCliente(CustomerDTO customerDTO) {
        this.borrar(new Customer(customerDTO));
    }

    public void borrarTodosLosClientes() {
        this.borrar();
    }
    public List<CustomerDTO> consultarTodosLosClientes() {
        List<CustomerDTO> customers = new ArrayList<>();
        this.buscar().forEach((customer -> {
            customers.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getCountry()));
        }));
        return customers;
    }

    public CustomerDTO consultarPorIdCliente(Long id) {
        Customer customer = this.buscarPorId(id);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getCountry());
    }

    public List<CustomerDTO> buscarPorNombreCliente(String name){
        List<CustomerDTO> customers = new ArrayList<>();
        this.buscarPorCampoValor("name", name).forEach((customer -> {
            customers.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getCountry()));
        }));
        return customers;
    }

    public List<CustomerDTO> dameListaCustomersDePaises(String prefixpais){
        List<CustomerDTO> customers = new ArrayList<>();
        repository.findAllByCountryContains(prefixpais).forEach((customer -> {
            customers.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getCountry()));
        }));
        return customers;
    }




}
