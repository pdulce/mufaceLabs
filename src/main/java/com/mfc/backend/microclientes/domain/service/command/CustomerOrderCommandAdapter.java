package com.mfc.backend.microclientes.domain.service.command;

import com.mfc.backend.microclientes.domain.model.command.CustomerOrder;
import com.mfc.infra.output.adapter.CommandAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandAdapter extends CommandAdapter<CustomerOrder> {

}
