package com.arch.mfc.application.message;

import com.arch.mfc.infra.message.CQRSKafkaMessageAbstract;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class CQRSKafkaMessageApp extends CQRSKafkaMessageAbstract {

    @KafkaListener(topicPattern = "myTopic*", groupId = "my-group")
    public void consumeEvent( @Payload( required = false ) String eventMsg ) {

        super.consumeEvent(eventMsg);
    }
    
}
