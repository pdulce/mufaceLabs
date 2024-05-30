package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.events.EventArch;
import com.arch.mfc.infra.inputport.QueryCQRSDocumentInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryDocumentInputAdapter<T> implements QueryCQRSDocumentInputPort<T> {

    @Autowired
    MongoRepository<T, String> repository;

    protected static final String GROUP_ID = "cqrs-query-adapter";

    @KafkaListener(topics = EventArch.EVENT_TOPIC, groupId = GROUP_ID)
    public void listen(EventArch<?> event) {
        Map<String, Object> eventData = ConversionUtils.convertLinkedHashMapToMap(event.getData());
        try {
            if (event.getTypeEvent().contentEquals(EventArch.EVENT_TYPE_CREATE)) {
                this.insertReg(eventData,
                        (Class<T>) Class.forName(event.getData().getClass().getName()));
            } else if (event.getTypeEvent().contentEquals(EventArch.EVENT_TYPE_UPDATE)) {
                this.updateReg(eventData,
                        (Class<T>) Class.forName(event.getData().getClass().getName()));
            } else if (event.getTypeEvent().contentEquals(EventArch.EVENT_TYPE_DELETE)) {
                this.deleteReg(event.getId());
            }

        } catch (ClassNotFoundException exc) {
            throw new RuntimeException("fatal error ", exc);
        }
    }

    @Override
    public void insertReg(Map<String, Object> reg, Class<T> clazz) {
        String json = ConversionUtils.map2Jsonstring(reg);
        T order = ConversionUtils.jsonStringToObject(json, clazz);
        this.repository.save(order);
    }

    @Override
    public void updateReg(Map<String, Object> reg, Class<T> clazz) {
        String json = ConversionUtils.map2Jsonstring(reg);
        T order = ConversionUtils.jsonStringToObject(json, clazz);
        this.repository.save(order);
    }

    @Override
    public void deleteReg(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public Map<String, Object> findById(String id) {
        Optional<T> order = this.repository.findById(id);
        return order.map(ConversionUtils::objectToMap).orElse(null);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        List<T> orders = this.repository.findAll();
        return orders.stream().map(ConversionUtils::objectToMap).collect(Collectors.toList());
    }

}