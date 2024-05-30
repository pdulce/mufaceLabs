package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.document.CustomerOrderDocument;
import com.arch.mfc.application.repository.document.CustomerOrderDocumentRepository;
import com.arch.mfc.infra.inputadapter.QueryDocumentInputAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderQueryService extends QueryDocumentInputAdapter<CustomerOrderDocument> {

    @Autowired
    CustomerOrderDocumentRepository repository;

    public CustomerOrderQueryService(CustomerOrderDocumentRepository concreteRepository) {
        super(concreteRepository);
    }


}
