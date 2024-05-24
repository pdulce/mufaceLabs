package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.document.CustomerOrderDocument;
import com.arch.mfc.application.repository.document.CustomerOrderDocumentRepository;
import com.arch.mfc.infra.outputadapter.nonrelational.MongoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderService extends MongoImpl<CustomerOrderDocument> {

    @Autowired
    CustomerOrderDocumentRepository repository;

    @Override
    protected MongoRepository<CustomerOrderDocument, String> getRepository() {
        return repository;
    }
}
