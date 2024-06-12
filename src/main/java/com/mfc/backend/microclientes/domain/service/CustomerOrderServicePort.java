package com.mfc.backend.microclientes.domain.service;

import com.mfc.backend.microclientes.api.dto.CustomerOrderDTO;

public interface CustomerOrderServicePort {

    CustomerOrderDTO crearPedido(CustomerOrderDTO customerOrderDTO);
}
