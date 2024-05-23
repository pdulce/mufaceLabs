package com.arch.mfc.infra.eventconsumers;

import com.arch.mfc.infra.inputport.QueryCQRSBrokerInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueryCQRSConsumerService {

    @Autowired
    QueryCQRSBrokerInputPort messageBrokerInputPort;

    protected static final String TOPIC_PATTERN = "topicCQRS*";

    protected static final String GROUP_ID = "cqrs-2";

    @KafkaListener(topicPattern = TOPIC_PATTERN, groupId = GROUP_ID)
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        if ( eventMsg == null ) {
            return;
        }
        Map<String, Object> event = ConversionUtils.jsonstring2Map( eventMsg );

        Map<String, Object> payload = (Map<String, Object>) event.get("payload");
        String operation = (String) payload.get("op");
        String table = (String) payload.get("almacen");

        if ( operation.equals("u") ) {
            messageBrokerInputPort.updateReg(table, (Map<String, Object>) payload.get("after"));
        } else if ( operation.equals("c") ) {
            messageBrokerInputPort.insertReg(table, (Map<String, Object>) payload.get("after"));
        } else if ( operation.equals("d") ) {
            messageBrokerInputPort.deleteReg(table, (Map<String, Object>) payload.get("before"));
        } 
    }
    
}
