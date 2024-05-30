package com.arch.mfc.infra.inputadapter;

import com.arch.mfc.infra.event.Event;
import com.arch.mfc.infra.inputport.EventConsumer;
import com.arch.mfc.infra.inputport.QueryInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.kafka.annotation.KafkaListener;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class QueryInputConsumerAdapter<T> implements QueryInputPort<T>, EventConsumer {
    @Autowired
    MongoRepository<T, String> repository;

    public abstract void listen(Event<?> eventArch);

    public void procesarEvento(Event<?> event) {

        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        if (event.getContextInfo() == null || event.getContextInfo().getAlmacen() == null
                || !entityClass.getSimpleName().equals(event.getContextInfo().getAlmacen())) {
            //dejo pasar este mensaje porque no es para este consumidor
            return;
        }

        if (event.getInnerEvent().getTypeEvent().contentEquals(Event.EVENT_TYPE_CREATE)
                || event.getInnerEvent().getTypeEvent().contentEquals(Event.EVENT_TYPE_UPDATE)) {
            saveReg((T) event.getInnerEvent().getData());
        } else if (event.getInnerEvent().getTypeEvent().contentEquals(Event.EVENT_TYPE_DELETE)) {
            this.deleteReg(event.getId());
        }
    }

    @Override
    public void saveReg(T document) {
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