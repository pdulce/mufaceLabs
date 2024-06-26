package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerDTO;
import com.mfc.backend.microclientes.domain.model.Customer;
import com.mfc.infra.output.adapter.ArqServiceRelationalDBAdapter;
import com.mfc.infra.output.port.ArqServicePort;

import java.util.List;

public interface CustomerArqRelationalServicePort extends ArqServicePort<Customer, CustomerDTO, Long> {

    List<CustomerDTO> dameListaCustomersDePaises (String paisPrefix);

}
