package com.arch.mfc.infra.outputadapter;

import com.arch.mfc.infra.event.Event;
import com.arch.mfc.infra.outputport.CommandEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommandEventPublisherAdapter implements CommandEventPublisher {

    private final KafkaTemplate<String, Event<?>> kafkaTemplate;

    @Autowired
    public CommandEventPublisherAdapter(KafkaTemplate<String, Event<?>> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, Event<?> eventArch) {
        kafkaTemplate.send(topic, eventArch.getId(), eventArch);
    }

}
