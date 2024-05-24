package com.arch.mfc.infra.eventconsumers;

import com.arch.mfc.infra.inputport.EventSourcingBrokerInputPort;
import com.arch.mfc.infra.outputadapter.nonrelational.MongoImpl;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EventConsumerService {

    //@Autowired
    EventSourcingBrokerInputPort<T> eventSourcingBrokerInputPort;

    protected static final String TOPIC_PATTERN = "topicCQRS*";

    protected static final String GROUP_ID = "cqrs-1";

    @KafkaListener(topicPattern = TOPIC_PATTERN, groupId = GROUP_ID)
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        if ( eventMsg == null ) {
            return;
        }
        Map<String, Object> event = ConversionUtils.jsonstring2Map( eventMsg );

        Map<String, Object> payload = (Map<String, Object>) event.get("payload");
        String documentClassname = (String) payload.get("document");

        try {
            eventSourcingBrokerInputPort.insertEvent((Map<String, Object>) payload.get("after"),
                    (Class<T>) Class.forName(documentClassname));
        } catch (ClassNotFoundException exc) {
            throw new RuntimeException("fatal error ", exc);
        }
    }
    
}
