package com.arch.mfc.infra.events;


import com.arch.mfc.infra.inputport.QueryCQRSDocumentInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;

@Service
public class QueryCQRSConsumer {

    /** Usaremos un adaptador para un port de BBDD no relacional orientada a Documentos como Mongo **/
    @Autowired
    QueryCQRSDocumentInputPort queryCQRSDocumentInputPort;

    protected static final String GROUP_ID = "cqrs-2";

    @KafkaListener(topics = EventArch.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(EventArch<?> event) {
        Map<String, Object> eventData = ConversionUtils.convertLinkedHashMapToMap(event.getData());
        try {
            if (event.getTypeEvent().contentEquals(EventArch.EVENT_TYPE_CREATE)) {
                queryCQRSDocumentInputPort.insertReg(eventData,
                        (Class<T>) Class.forName(event.getData().getClass().getName()));
            } else if (event.getTypeEvent().contentEquals(EventArch.EVENT_TYPE_UPDATE)) {
                queryCQRSDocumentInputPort.updateReg(eventData,
                        (Class<T>) Class.forName(event.getData().getClass().getName()));
            }

        } catch (ClassNotFoundException exc) {
            throw new RuntimeException("fatal error ", exc);
        }
    }

    /*@KafkaListener(topics = "events", groupId = GROUP_ID)
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        if ( eventMsg == null ) {
            return;
        }
        Map<String, Object> event = ConversionUtils.jsonstring2Map( eventMsg );
        Map<String, Object> payload = (Map<String, Object>) event.get("payload");
        String operation = (String) payload.get("op");

        String documentClassname = (String) payload.get("document");

        try {
            if ( operation.equals("c")) {
                queryCQRSDocumentInputPort.insertReg((Map<String, Object>) payload.get("after"),
                        (Class<T>) Class.forName(documentClassname));
            } else if (operation.equals("u")) {
                queryCQRSDocumentInputPort.updateReg((Map<String, Object>) payload.get("after"),
                        (Class<T>) Class.forName(documentClassname));
            }

        } catch (ClassNotFoundException exc) {
            throw new RuntimeException("fatal error ", exc);
        }
    }*/
    
}
