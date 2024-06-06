package com.arch.mfc.backendA.domain.service.command;

import com.arch.mfc.backendA.domain.model.CustomerOrder;
import com.arch.mfc.infra.outputadapter.CommandAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandAdapter extends CommandAdapter<CustomerOrder> {
    public String getDocumentEntityClassname() {
        return "CustomerOrderDocument";
    }

}
