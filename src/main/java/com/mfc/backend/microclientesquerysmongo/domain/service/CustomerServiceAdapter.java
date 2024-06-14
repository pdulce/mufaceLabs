package com.mfc.backend.microclientesquerysmongo.domain.service;

import com.mfc.backend.microclientesquerysmongo.api.dto.CustomerDocumentDTO;
import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.backend.microclientesquerysmongo.domain.repository.CustomerDocumentRepository;
import com.mfc.infra.output.adapter.ArqMongoServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceAdapter extends ArqMongoServiceAdapter<CustomerDocument, CustomerDocumentDTO, String> {

    @Autowired
    CustomerDocumentRepository customerDocumentRepository;

    @Override
    protected MongoRepository<CustomerDocument, String> getRepository() {
        return this.customerDocumentRepository;
    }
}
