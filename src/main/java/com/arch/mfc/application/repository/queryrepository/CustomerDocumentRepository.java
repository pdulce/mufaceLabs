package com.arch.mfc.application.repository.queryrepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.arch.mfc.application.domain.querydomain.CustomerDocument;



public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, String> {

}
