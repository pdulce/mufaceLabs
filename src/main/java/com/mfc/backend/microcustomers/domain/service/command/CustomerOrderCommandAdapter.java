package com.mfc.backend.microcustomers.domain.service.command;

import com.mfc.backend.microcustomers.domain.model.command.CustomerOrder;
import com.mfc.infra.outputadapter.CommandAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandAdapter extends CommandAdapter<CustomerOrder> {
    public String getDocumentEntityClassname() {
        return "CustomerOrderDocument";
    }

}
