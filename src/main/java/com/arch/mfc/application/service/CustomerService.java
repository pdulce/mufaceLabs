package com.arch.mfc.application.service;

import com.arch.mfc.application.domain.command.Customer;
import com.arch.mfc.application.repository.CustomerRepository;
import com.arch.mfc.infra.outputadapter.relational.GenericJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService extends GenericJpaService<Customer> {

    public CustomerService() {
    }

    public CustomerService(Class<Customer> entityClass) {

        super(entityClass);
    }

    /** personalized operations not in infra : acceder al repositorio de la infra y consultar **/

    public List<Customer> getByName(String name) {
        Customer plantilla = new Customer();
        plantilla.setName(name);
        Example<Customer> filter = Example.of(plantilla);
        return repository.findAll(filter);
    }

}
