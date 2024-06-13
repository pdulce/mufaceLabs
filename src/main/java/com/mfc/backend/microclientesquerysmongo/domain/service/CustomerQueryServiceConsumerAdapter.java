package com.mfc.backend.microclientesquerysmongo.domain.service;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.infra.output.adapter.MongoOperationsAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryServiceConsumerAdapter extends MongoOperationsAdapter<CustomerDocument> {

}
