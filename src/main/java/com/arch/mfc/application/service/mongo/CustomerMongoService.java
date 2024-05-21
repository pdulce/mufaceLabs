package com.arch.mfc.application.service.mongo;

import com.arch.mfc.application.domain.query.MongoCustomer;
import com.arch.mfc.application.repository.CustomersMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerMongoService {

    @Autowired
    private CustomersMongoRepository  customersMongoRepository;

    public List<MongoCustomer> getAllUsers() {
        return customersMongoRepository.findAll();
    }

    public MongoCustomer getUserByEmail(String email) {
        return customersMongoRepository.findByEmail(email);
    }

    public MongoCustomer saveUser(MongoCustomer user) {
        return customersMongoRepository.save(user);
    }

    public void deleteUser(String id) {
        customersMongoRepository.deleteById(id);
    }
}