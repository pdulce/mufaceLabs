package com.mfc.backend.microcustomers.domain.repository.query;

import com.mfc.backend.microcustomers.domain.model.query.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerOrderDocumentRepository extends MongoRepository<CustomerOrderDocument, String> {

}
