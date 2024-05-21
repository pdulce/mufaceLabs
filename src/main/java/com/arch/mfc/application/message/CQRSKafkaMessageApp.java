package com.arch.mfc.application.message;

import com.arch.mfc.infra.message.QueryConsumerServiceMsgBroker;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CQRSKafkaMessageApp extends QueryConsumerServiceMsgBroker {

    @KafkaListener(topicPattern = "topicCQRS*", groupId = "my-group")
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        super.consumeEvent(eventMsg);
    }

}
