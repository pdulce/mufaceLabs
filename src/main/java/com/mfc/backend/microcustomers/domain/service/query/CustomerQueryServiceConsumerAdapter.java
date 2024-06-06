package com.mfc.backend.microcustomers.domain.service.query;

import com.arch.mfc.backendA.domain.model.query.CustomerDocument;
import com.arch.mfc.infra.event.Event;
import com.arch.mfc.infra.inputadapter.QueryInputConsumerAdapter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryServiceConsumerAdapter extends QueryInputConsumerAdapter<com.arch.mfc.backend.micro.customers.domain.model.query.CustomerDocument> {

    private static final String GROUP_ID = "cqrs-query-adapter-2";
    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        super.procesarEvento(event);
    }

}
