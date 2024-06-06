package com.arch.mfc.backendA.repository.queryrepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.arch.mfc.backendA.domain.querydomain.CustomerDocument;



public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, String> {

}
