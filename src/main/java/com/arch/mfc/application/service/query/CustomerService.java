package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.document.CustomerDocument;
import com.arch.mfc.application.repository.document.CustomerDocumentRepository;
import com.arch.mfc.infra.outputadapter.nonrelational.MongoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends MongoImpl<CustomerDocument> {

    @Autowired
    CustomerDocumentRepository repository;

    @Override
    protected MongoRepository<CustomerDocument, String> getRepository() {
        return repository;
    }
}
