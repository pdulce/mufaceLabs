package com.arch.mfc.application.repository.queryrepository;

import com.arch.mfc.application.domain.querydomain.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerOrderDocumentRepository extends MongoRepository<CustomerOrderDocument, String> {

}
