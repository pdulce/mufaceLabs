package com.arch.mfc.infra.eventconsumers;

import com.arch.mfc.infra.inputport.EventMessageBrokerInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EventConsumerService {

    @Autowired
    EventMessageBrokerInputPort eventMessageBrokerInputPort;

    protected static final String TOPIC_PATTERN = "topicCQRS*";

    protected static final String GROUP_ID = "cqrs";

    @KafkaListener(topicPattern = TOPIC_PATTERN, groupId = GROUP_ID)
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        if ( eventMsg == null ) {
            return;
        }
        Map<String, Object> event = ConversionUtils.jsonstring2Map( eventMsg );

        Map<String, Object> payload = (Map<String, Object>) event.get("payload");
        String table = (String) payload.get("table");

        eventMessageBrokerInputPort.insertEvent(table, (Map<String, Object>) payload.get("after"));
    }
    
}
