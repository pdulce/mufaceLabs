package com.mfc.backend.microclientesquerysmongo.domain.repository;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, String> {

    //implementar HQL


}
