package com.arch.mfc.infra.events.adapter;

import com.arch.mfc.infra.events.EventArch;
import com.arch.mfc.infra.inputport.EventStoreInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventKeyValueDomainConsumer {
    protected static final String GROUP_ID = "event-adapter";

    @Autowired
    EventStoreInputPort eventStore;

    @KafkaListener(topicPattern = EventArch.EVENT_TOPIC_PATTERN, groupId = GROUP_ID)
    public void listen(EventArch<Object> eventArch) {
        eventStore.saveEvent(eventArch);
    }

}
