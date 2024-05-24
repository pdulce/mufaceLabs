package com.arch.mfc.application.repository.document;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.arch.mfc.application.domain.document.CustomerDocument;


public interface CustomerDocumentRepository extends MongoRepository<CustomerDocument, String> {

}
