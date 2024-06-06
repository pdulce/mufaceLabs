package com.mfc.backend.microcustomers.domain.service.query;

import com.mfc.backend.microcustomers.domain.model.query.CustomerDocument;
import com.mfc.infra.event.Event;
import com.mfc.infra.input.adapter.QueryInputConsumerAdapter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryServiceConsumerAdapter extends QueryInputConsumerAdapter<CustomerDocument> {

    private static final String GROUP_ID = "cqrs-query-adapter-2";
    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        super.procesarEvento(event);
    }

}
