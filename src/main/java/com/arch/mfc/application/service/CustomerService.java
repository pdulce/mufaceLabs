package com.arch.mfc.application.service;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.repository.CustomerRepository;
import com.arch.mfc.infra.outputadapter.relational.CrudOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends CrudOperation {
    @Autowired
    CustomerRepository entityRepository;

    @Override
    protected JpaRepository getJPaRepository() {

        return entityRepository;
    }


    /** personalized operations not in infra **/

    public List<Customer> getByName(String name) {
        return entityRepository.findByName(name);
    }

}
