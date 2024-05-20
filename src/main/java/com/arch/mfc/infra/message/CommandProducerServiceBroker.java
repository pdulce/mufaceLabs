package com.arch.mfc.infra.message;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommandProducerServiceBroker {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CommandProducerServiceBroker(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
