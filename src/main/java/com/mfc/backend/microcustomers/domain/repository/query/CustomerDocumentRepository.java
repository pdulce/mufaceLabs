package com.mfc.backend.microcustomers.domain.repository.query;

import com.mfc.backend.microcustomers.domain.model.query.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, String> {

}
