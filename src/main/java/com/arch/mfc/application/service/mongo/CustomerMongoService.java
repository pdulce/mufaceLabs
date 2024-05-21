package com.arch.mfc.application.service.mongo;

import com.arch.mfc.application.domain.query.MongoCustomer;
import com.arch.mfc.application.repository.CustomerQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerMongoService {

    @Autowired
    private CustomerQueryRepository customerQueryRepository;

    public List<MongoCustomer> getAllUsers() {
        return customerQueryRepository.findAll();
    }

    public MongoCustomer getUserByEmail(String email) {
        return customerQueryRepository.findByEmail(email);
    }

    public MongoCustomer saveUser(MongoCustomer user) {
        return customerQueryRepository.save(user);
    }

    public void deleteUser(String id) {
        customerQueryRepository.deleteById(id);
    }
}