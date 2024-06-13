package com.mfc.infra.input.adapter;

import com.mfc.infra.configuration.ConfigProperties;
import com.mfc.infra.event.Event;
import com.mfc.infra.input.port.CommandEventListenerPort;
import com.mfc.infra.input.port.EventStoreInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class CommandCommandEventListenerAdapter implements CommandEventListenerPort {

    protected static final String GROUP_ID = "event-adapter";
    Logger logger = LoggerFactory.getLogger(CommandCommandEventListenerAdapter.class);

    @Autowired
    ConfigProperties configProperties;

    @Autowired
    EventStoreInputPort eventStoreInputPort;

    @KafkaListener(topics = Event.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(Event<?> eventArch) {
        procesarEvento(eventArch);
    }

    public void procesarEvento(Event<?> eventArch) {
        if (!configProperties.isEventBrokerActive()) {
            logger.error("Debe tener activa la configuración de uso de mensajería en la arquitectura");
            return;
        }
        this.eventStoreInputPort.saveEvent("audit",
                eventArch.getContextInfo().getApplicationId(), eventArch.getContextInfo().getAlmacen(),
                eventArch.getId(), eventArch);
    }


}
