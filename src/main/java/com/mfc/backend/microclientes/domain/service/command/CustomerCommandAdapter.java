package com.mfc.backend.microclientes.domain.service.command;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.infra.output.adapter.CommandAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommandAdapter extends CommandAdapter<Customer> {

    /** personalized operations not in infra : acceder al repositorio de la infra y consultar **/

    public String getDocumentEntityClassname() {
        return "CustomerDocument";
    }


}