package com.mfc.backend.microclientes.domain.service.command;

import com.mfc.backend.microclientes.domain.model.command.Customer;
import com.mfc.infra.output.adapter.CommandServiceAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerCommandAdapterService extends CommandServiceAdapter<Customer, Long> {

    // personalized methods

    public String hello(){
        this.findAllByFieldvalue("name", "Pepito");
        //
        return "hello";
    }




}
