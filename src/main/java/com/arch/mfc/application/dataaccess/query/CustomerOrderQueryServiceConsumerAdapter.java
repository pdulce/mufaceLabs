package com.arch.mfc.application.dataaccess.query;

import com.arch.mfc.application.domain.querydomain.CustomerOrderDocument;
import com.arch.mfc.infra.event.Event;
import com.arch.mfc.infra.inputadapter.QueryInputConsumerAdapter;
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
