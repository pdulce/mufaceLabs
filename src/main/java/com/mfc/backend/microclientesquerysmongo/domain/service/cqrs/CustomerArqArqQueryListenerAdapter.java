package com.mfc.backend.microclientesquerysmongo.domain.service.cqrs;

import com.mfc.backend.microclientesquerysmongo.api.dto.CustomerDocumentDTO;
import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.infra.event.ArqEvent;
import com.mfc.infra.input.adapter.ArqQueryDomainListenerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "arch.event-broker-active", havingValue = "true", matchIfMissing = false)
public class CustomerArqArqQueryListenerAdapter extends ArqQueryDomainListenerAdapter<CustomerDocument, CustomerDocumentDTO, String> {

    private static final String GROUP_ID = "cqrs-query-adapter-2";
    @KafkaListener(topics = ArqEvent.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(ArqEvent<?> event) {
        super.procesarEvento(event);
    }

}
