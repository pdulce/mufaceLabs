package com.arch.mfc.infra.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommandPublisher implements EventPublisher {

    private final KafkaTemplate<String, EventArch<?>> kafkaTemplate;

    public CommandPublisher(KafkaTemplate<String, EventArch<?>> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, EventArch<?> eventArch) {
        kafkaTemplate.send(topic.concat("-").concat(eventArch.getAlmacen()), eventArch.getId(), eventArch);
    }

}
