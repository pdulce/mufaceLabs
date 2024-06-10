package com.mfc.backend.microclientes.domain.service.query;

import com.mfc.backend.microclientes.domain.model.query.CustomerOrderDocument;
import com.mfc.infra.event.Event;
import com.mfc.infra.input.adapter.QueryInputConsumerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(name = "arch.eventbroker.active", havingValue = "true", matchIfMissing = false)
public class CustomerOrderQueryServiceConsumerAdapter extends QueryInputConsumerAdapter<CustomerOrderDocument> {

    private static final String GROUP_ID = "cqrs-query-adapter-1";
    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> event) {
        super.procesarEvento(event);
    }

}
