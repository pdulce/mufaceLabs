package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;
import com.mfc.backend.microclientes.domain.model.CustomerOrder;
import com.mfc.infra.output.port.ArqServicePort;

public interface CustomerOrderServicePort extends ArqServicePort<CustomerOrder, CustomerOrderDTO, Long> {

}
