package com.arch.mfc.application.service.mongo;

import com.arch.mfc.application.domain.query.MongoCustomer;
import com.arch.mfc.infra.outputadapter.nonrelational.GenericQueryMongoImpl;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerMongoService extends GenericQueryMongoImpl<MongoCustomer> {

    public CustomerMongoService() {}
    public CustomerMongoService(Class<MongoCustomer> entityClass) {
        super(entityClass);
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