package com.mfc.backend.microclientesquerysmongo.domain.repository;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerOrderDocumentRepository extends MongoRepository<CustomerOrderDocument, String> {

}
