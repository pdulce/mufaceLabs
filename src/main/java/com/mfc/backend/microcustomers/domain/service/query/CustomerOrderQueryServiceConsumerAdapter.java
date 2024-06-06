package com.mfc.backend.microcustomers.domain.service.query;

import com.mfc.backend.microcustomers.domain.model.query.CustomerOrderDocument;
import com.mfc.infra.event.Event;
import com.mfc.infra.input.adapter.QueryInputConsumerAdapter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderQueryServiceConsumerAdapter extends QueryInputConsumerAdapter<CustomerOrderDocument> {

    private static final String GROUP_ID = "cqrs-query-adapter-1";
    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        super.procesarEvento(event);
    }

}
