package com.mfc.backend.microcustomers.domain.repository.query;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface CustomerDocumentRepository extends MongoRepository<com.arch.mfc.backend.micro.customers.domain.model.query.CustomerDocument, String> {

}
