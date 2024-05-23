package com.arch.mfc.application.repository.query;

import com.arch.mfc.application.domain.document.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerQueryRepository extends MongoRepository<CustomerDocument, String> {

    CustomerDocument findByName(String name);


}