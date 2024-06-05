package com.arch.mfc.application.dataaccess.command;

import com.arch.mfc.application.domain.CustomerOrder;
import com.arch.mfc.infra.outputadapter.CommandAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerOrderCommandAdapter extends CommandAdapter<CustomerOrder> {
    public String getDocumentEntityClassname() {
        return "CustomerOrderDocument";
    }

}
