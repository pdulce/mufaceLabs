package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.infra.output.port.CommandServicePort;

import java.util.List;

public interface CustomerCommandServicePort extends CommandServicePort<Customer, Long> {

    List<Customer> dameListaCustomersDePaises (String paisPrefix);

}
