package com.arch.mfc.application.service.mongo;

import com.arch.mfc.application.domain.query.MongoCustomer;
import com.arch.mfc.application.repository.query.CustomerQueryRepository;
import com.arch.mfc.infra.outputadapter.nonrelational.GenericQueryMongoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerMongoService extends GenericQueryMongoImpl<MongoCustomer> {

    @Autowired
    CustomerQueryRepository customerQueryRepository;

    public CustomerMongoService() {}
    public CustomerMongoService(MongoRepository<MongoCustomer, String> repository) {

        this.repository = repository;
    }

    public List<MongoCustomer> getAllUsers() {
        return repository.findAll();
    }

    public List<MongoCustomer> getByName(String name) {
        MongoCustomer plantilla = new MongoCustomer();
        plantilla.setName(name);
        Example<MongoCustomer> filter = Example.of(plantilla);
        return repository.findAll(filter);
    }

    public MongoCustomer saveUser(MongoCustomer user) {
        return repository.save(user);
    }

    public void deleteUser(String id) {
        repository.deleteById(id);
    }
}