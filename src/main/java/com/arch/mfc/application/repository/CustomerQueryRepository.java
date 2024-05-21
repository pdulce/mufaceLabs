package com.arch.mfc.application.repository;

import com.arch.mfc.application.domain.query.MongoCustomer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerQueryRepository extends MongoRepository<MongoCustomer, String> {

    MongoCustomer findByName(String name);


}