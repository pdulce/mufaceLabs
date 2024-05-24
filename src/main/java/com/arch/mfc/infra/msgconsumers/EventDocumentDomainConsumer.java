package com.arch.mfc.infra.msgconsumers;

import com.arch.mfc.infra.inputport.EventSourcingDocumentInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

//@Service
public class EventDocumentDomainConsumer {

    /** Usaremos un adaptador para un port de BBDD no relacional de tipo Key-Value como Redis **/

    //@Autowired
    EventSourcingDocumentInputPort<T> eventSourcingDocumentInputPort;

    protected static final String TOPIC_PATTERN = "no-recuperar-topicCQRS*";

    protected static final String GROUP_ID = "cqrs-1";

    @KafkaListener(topicPattern = TOPIC_PATTERN, groupId = GROUP_ID)
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        if ( eventMsg == null ) {
            return;
        }
        Map<String, Object> event = ConversionUtils.jsonstring2Map( eventMsg );
        Map<String, Object> payload = (Map<String, Object>) event.get("payload");
        String operation = (String) payload.get("op");

        String documentClassname = (String) payload.get("document");

        /*try {

            //eventSourcingDocumentInputPort.insertEvent((Map<String, Object>) payload.get("after"),
            //       (Class<T>) Class.forName(documentClassname));

        } catch (ClassNotFoundException exc) {
            throw new RuntimeException("fatal error ", exc);
        }*/
    }

}
