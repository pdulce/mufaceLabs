package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.document.CustomerDocument;
import com.arch.mfc.application.repository.document.CustomerDocumentRepository;
import com.arch.mfc.infra.inputadapter.QueryDocumentInputAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryService extends QueryDocumentInputAdapter<CustomerDocument> {

    @Autowired
    CustomerDocumentRepository repository;

    public CustomerQueryService(CustomerDocumentRepository concreteRepository) {
        super(concreteRepository);
    }

}
