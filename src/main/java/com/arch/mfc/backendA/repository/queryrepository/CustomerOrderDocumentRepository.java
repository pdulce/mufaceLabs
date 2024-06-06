package com.arch.mfc.backendA.repository.queryrepository;

import com.arch.mfc.backendA.domain.querydomain.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerOrderDocumentRepository extends MongoRepository<CustomerOrderDocument, String> {

}
