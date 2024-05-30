package com.arch.mfc.application.service.command;

import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.infra.outputadapter.CommandAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandAdapter extends CommandAdapter<CustomerOrder> {

}
