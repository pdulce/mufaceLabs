package com.arch.mfc.application.service.command;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.infra.outputadapter.relational.GenericJpaCommandService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCommandCommandService extends GenericJpaCommandService<Customer> {

    /** personalized operations not in infra : acceder al repositorio de la infra y consultar **/



}
