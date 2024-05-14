package com.arch.mfc.application.message;

import com.arch.mfc.infra.message.CQRSKafkaMessageAbstract;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CQRSKafkaMessageApp extends CQRSKafkaMessageAbstract {

    @KafkaListener(topicPattern = "dbserver1.public.*", groupId = "group1")
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        super.consumeEvent(eventMsg);
    }
    
}
