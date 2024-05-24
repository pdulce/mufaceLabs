package com.arch.mfc.application.repository.document;

import com.arch.mfc.application.domain.document.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerOrderDocumentRepository extends MongoRepository<CustomerOrderDocument, String> {

}
