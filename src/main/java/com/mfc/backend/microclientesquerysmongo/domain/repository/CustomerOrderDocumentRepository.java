package com.mfc.backend.microclientesquerysmongo.domain.repository;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderDocumentRepository extends MongoRepository<CustomerOrderDocument, String> {

}
