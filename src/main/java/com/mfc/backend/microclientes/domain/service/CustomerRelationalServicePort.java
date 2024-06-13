package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.infra.output.port.RelationalOperationsPort;

import java.util.List;

public interface CustomerRelationalServicePort extends RelationalOperationsPort<Customer, Long> {

    List<CustomerDTO> dameListaCustomersDePaises (String paisPrefix);

    CustomerDTO actualizarCliente(CustomerDTO customerDTO);

    void borrarCliente(CustomerDTO customerDTO);

    void borrarTodosLosClientes();

    List<CustomerDTO> consultarTodosLosClientes();

    CustomerDTO consultarPorIdCliente(Long id);

    List<CustomerDTO> buscarPorNombreCliente(String name);

}
