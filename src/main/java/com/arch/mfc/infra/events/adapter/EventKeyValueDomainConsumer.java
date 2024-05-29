package com.arch.mfc.infra.events.adapter;

import com.arch.mfc.infra.inputport.EventStoreInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventKeyValueDomainConsumer {

    /** Usaremos un adaptador para un port de BBDD no relacional de tipo Key-Value como Redis **/
    //@Autowired
    //EventSourcingKeyValueInputPort eventSourcingKeyValueInputPort;

    protected static final String GROUP_ID = "cqrs-1";

    @Autowired
    EventStoreInputPort eventStore;

    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<Object> event) {
        eventStore.saveEvent(event);
    }

    /*@KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        if ( eventMsg == null ) {
            return;
        }

        Map<String, Object> event = ConversionUtils.jsonstring2Map( eventMsg );
        Map<String, Object> payload = (Map<String, Object>) event.get("payload");
        String operation = (String) payload.get("op");
        String table = (String) payload.get("almacen");

        if ( operation.equals("u") || operation.equals("c")) {
            eventSourcingKeyValueInputPort.insertEvent(table, (Map<String, Object>) payload.get("after"));
        } else if ( operation.equals("d") ) {
            eventSourcingKeyValueInputPort.insertEvent(table, (Map<String, Object>) payload.get("before"));
        }
    }*/

}
