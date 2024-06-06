package com.arch.mfc.backendA.domain.repository.queryrepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.arch.mfc.backendA.domain.model.querydomain.CustomerDocument;



public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, String> {

}
