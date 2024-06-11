package com.mfc.backend.microclientes.domain.service.command;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.infra.output.port.CommandServicePort;

import java.util.List;

public interface CustomerCommandServicePort extends CommandServicePort<Customer, Long> {

    public List<Customer> dameListaCustomersDePaises (String paisPrefix);

}
