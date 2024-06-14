package com.mfc.backend.microclientesquerysmongo.domain.service;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.infra.output.adapter.MongoServiceAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryServiceConsumerAdapter extends MongoServiceAdapter<CustomerDocument> {

}
