package com.mfc.backend.microclientesquerysmongo.domain.service;

import com.mfc.backend.microclientesquerysmongo.api.dto.CustomerDocumentOrderDTO;
import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerOrderDocument;
import com.mfc.backend.microclientesquerysmongo.domain.repository.CustomerOrderDocumentRepository;
import com.mfc.infra.output.adapter.ArqMongoServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderServiceAdapter extends ArqMongoServiceAdapter<CustomerOrderDocument, CustomerDocumentOrderDTO, String> {

    @Autowired
    private CustomerOrderDocumentRepository repository;

    protected MongoRepository<CustomerOrderDocument, String> getRepository() {
        return this.repository;
    }


}
