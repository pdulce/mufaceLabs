package com.mfc.backend.microclientes.domain.repository.query;

import com.mfc.backend.microclientes.domain.model.query.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, String> {

}
