package com.mfc.backend.microclientesquerysmongo.domain.service;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.infra.event.Event;
import com.mfc.infra.output.adapter.MongoRepositoryAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryServiceConsumerAdapter extends MongoRepositoryAdapter<CustomerDocument> {

}
