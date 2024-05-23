package com.arch.mfc.infra.outputadapter.relational;

import com.arch.mfc.infra.inputport.GenericInputPort;
import com.arch.mfc.infra.message.CommandProducerServiceBroker;
import com.arch.mfc.infra.utils.ConversionUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class GenericJpaCommandService<T> implements GenericInputPort<T> {

    @Autowired
    CommandProducerServiceBroker commandProducerServiceBroker;
    @Autowired
    protected JpaRepository<T, Long> repository;

    private Class<T> entityClass;

    public GenericJpaCommandService() {}
    public GenericJpaCommandService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        T saved = repository.save(entity);
        if (saved != null) {
            // cqrs artesanal
            Map<String, Object> intercambio = new HashMap<>();
            intercambio.put("payload", new HashMap<String, Object>());
            ((Map<String, Object>) intercambio.get("payload")).put("op", "c");
            ((Map<String, Object>) intercambio.get("payload")).put("table", entity.getClass().getSimpleName());
            ((Map<String, Object>) intercambio.get("payload")).put("after", ConversionUtils.convertToMap(saved));

            commandProducerServiceBroker.sendMessage("topicCQRS", ConversionUtils.map2Jsonstring(intercambio));
        }
        return saved;
    }

    @Override
    public T update(T entity) {
        T updated =  repository.save(entity);
        if (updated != null) {
            // cqrs artesanal
            Map<String, Object> intercambio = new HashMap<>();
            intercambio.put("payload", new HashMap<String, Object>());
            ((Map<String, Object>) intercambio.get("payload")).put("op", "u");
            ((Map<String, Object>) intercambio.get("payload")).put("table", entity.getClass().getSimpleName());
            ((Map<String, Object>) intercambio.get("payload")).put("after", ConversionUtils.convertToMap(updated));

            commandProducerServiceBroker.sendMessage("topicCQRS", ConversionUtils.map2Jsonstring(intercambio));
        }
        return updated;
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
        // cqrs artesanal
        Map<String, Object> intercambio = new HashMap<>();
        intercambio.put("payload", new HashMap<String, Object>());
        ((Map<String, Object>) intercambio.get("payload")).put("op", "d");
        ((Map<String, Object>) intercambio.get("payload")).put("table", entity.getClass().getSimpleName());
        ((Map<String, Object>) intercambio.get("payload")).put("before", ConversionUtils.convertToMap(entity));

        commandProducerServiceBroker.sendMessage("topicCQRS", ConversionUtils.map2Jsonstring(intercambio));
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<T> findAll() {
        return repository.findAll().stream().toList();
    }

    public Object getId(T entity) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return idField.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to get id field value", e);
        }
    }

}
