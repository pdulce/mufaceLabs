package com.mfc.backend.microclientesquerysmongo.domain.service;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerOrderDocument;
import com.mfc.infra.output.adapter.MongoOperationsAdapter;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderQueryServiceConsumerAdapter extends MongoOperationsAdapter<CustomerOrderDocument> {

}
