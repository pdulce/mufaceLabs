package com.arch.mfc.application.service.command;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.infra.outputadapter.CommandAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommandAdapter extends CommandAdapter<Customer> {

    /** personalized operations not in infra : acceder al repositorio de la infra y consultar **/

    public String getDocumentEntityClassname() {
        return "CustomerDocument";
    }


}
