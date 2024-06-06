package com.arch.mfc.backendA.domain.repository.queryrepository;

import com.arch.mfc.backendA.domain.model.querydomain.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerOrderDocumentRepository extends MongoRepository<CustomerOrderDocument, String> {

}
