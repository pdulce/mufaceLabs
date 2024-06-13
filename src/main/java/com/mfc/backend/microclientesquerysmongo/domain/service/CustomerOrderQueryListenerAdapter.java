package com.mfc.backend.microclientesquerysmongo.domain.service;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerOrderDocument;
import com.mfc.infra.event.Event;
import com.mfc.infra.input.adapter.QueryDomainDomainListenerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class CustomerOrderQueryListenerAdapter extends QueryDomainDomainListenerAdapter<CustomerOrderDocument> {

    private static final String GROUP_ID = "cqrs-query-adapter-2";
    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        super.procesarEvento(event);
    }

}
