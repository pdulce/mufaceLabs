package com.arch.mfc.application.repository;

import com.arch.mfc.application.domain.query.MongoCustomer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomersMongoRepository extends MongoRepository<MongoCustomer, String> {

    MongoCustomer findByName(String name);

    MongoCustomer findByEmail(String email);
}