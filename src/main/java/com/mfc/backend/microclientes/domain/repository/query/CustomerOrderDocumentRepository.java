package com.mfc.backend.microclientes.domain.repository.query;

import com.mfc.backend.microclientes.domain.model.query.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerOrderDocumentRepository extends MongoRepository<CustomerOrderDocument, String> {

}
