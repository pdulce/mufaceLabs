package com.mfc.backend.microclientes.domain.service.command;

import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandAdapterService extends CommandServiceAdapter<CustomerOrder, Long> {

}
