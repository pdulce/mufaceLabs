package com.mfc.infra.input.adapter;

import com.mfc.infra.configuration.EventBrokerProperties;
import com.mfc.infra.event.Event;
import com.mfc.infra.input.port.EventConsumer;
import com.mfc.infra.input.port.QueryInputPort;
import com.mfc.infra.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class QueryInputConsumerAdapter<T> implements QueryInputPort<T>, EventConsumer {
    Logger logger = LoggerFactory.getLogger(QueryInputConsumerAdapter.class);

    @Autowired
    EventBrokerProperties eventBrokerProperties;
    @Autowired
    MongoRepository<T, String> repository;

    public abstract void listen(Event<?> eventArch);

    public void procesarEvento(Event<?> event) {
        if (!eventBrokerProperties.isActive()) {
            logger.error("Debe tener activa la configuración de uso de mensajería en la arquitectura");
            return;
        }
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        if (event.getContextInfo() == null || event.getContextInfo().getAlmacen() == null
                || !entityClass.getSimpleName().equals(event.getContextInfo().getAlmacen())) {
            //dejo pasar este mensaje porque no es para este consumidor
            return;
        }

        if (event.getInnerEvent().getTypeOfEvent().contentEquals(Event.EVENT_TYPE_CREATE)
                || event.getInnerEvent().getTypeOfEvent().contentEquals(Event.EVENT_TYPE_UPDATE)) {
            saveReg((LinkedHashMap) event.getInnerEvent().getData(), entityClass);
        } else if (event.getInnerEvent().getTypeOfEvent().contentEquals(Event.EVENT_TYPE_DELETE)) {
            this.deleteReg(event.getId());
        }
    }

    @Override
    public void saveReg(LinkedHashMap deserialized, Class<T> entityClass) {
        T document = ConversionUtils.jsonStringToObject(ConversionUtils.map2Jsonstring(deserialized), entityClass);
        this.repository.save(document);
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