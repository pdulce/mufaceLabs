package com.mfc.infra.output.adapter;

import com.mfc.infra.event.Event;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class CommandEventPublisherPortAdapter implements CommandEventPublisherPort {

    Logger logger = LoggerFactory.getLogger(CommandEventPublisherPortAdapter.class);
    private final KafkaTemplate<String, Event<?>> kafkaTemplate;

    @Autowired
    public CommandEventPublisherPortAdapter(KafkaTemplate<String, Event<?>> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, Event<?> eventArch) {
        kafkaTemplate.send(topic, eventArch.getId(), eventArch);
    }

}
