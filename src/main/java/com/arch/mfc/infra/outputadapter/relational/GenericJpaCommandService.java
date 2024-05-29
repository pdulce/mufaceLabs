package com.arch.mfc.infra.outputadapter.relational;

import com.arch.mfc.infra.events.EventArch;
import com.arch.mfc.infra.outputport.GenericCommandPort;
import com.arch.mfc.infra.events.CommandPublisher;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@Transactional
public class GenericJpaCommandService<T> implements GenericCommandPort<T> {

    @Autowired
    CommandPublisher commandPublisher;
    @Autowired
    protected JpaRepository<T, Long> repository;

    @Override
    public T save(T entity) {
        T saved = repository.save(entity);
        if (saved != null) {
            /*Map<String, Object> intercambio = new HashMap<>();
            intercambio.put("payload", new HashMap<String, Object>());
            ((Map<String, Object>) intercambio.get("payload")).put("op", "c");

            String pathClass = entity.getClass().getName().substring(0,
                    entity.getClass().getName().lastIndexOf("."));
            String entityname = entity.getClass().getSimpleName();

            ((Map<String, Object>) intercambio.get("payload")).put("almacen", (entityname + "s").toLowerCase());
            ((Map<String, Object>) intercambio.get("payload")).put("document", pathClass + ".document." +
                    entityname + "Document");
            ((Map<String, Object>) intercambio.get("payload")).put("classname", entity.getClass().getName());
            ((Map<String, Object>) intercambio.get("payload")).put("after", ConversionUtils.convertToMap(saved));*/

            /*** Mando el evento al bus para que los recojan los dos consumers:
             *  - el responsable del dominio de eventos que persiste en MongoDB (patrón Event-Sourcing)
             *  - el responsable del dominio de consultas que persiste en Redis (patrón CQRS)
             */
            EventArch eventArch = new EventArch(UUID.randomUUID().toString(), EventArch.EVENT_TYPE_CREATE, entity);
            commandPublisher.publish(EventArch.EVENT_TOPIC, /*ConversionUtils.map2Jsonstring(intercambio)*/eventArch);
        }
        return saved;
    }

    @Override
    public T update(T entity) {
        T updated =  repository.save(entity);
        if (updated != null) {
            // cqrs artesanal
            /*Map<String, Object> intercambio = new HashMap<>();
            intercambio.put("payload", new HashMap<String, Object>());
            ((Map<String, Object>) intercambio.get("payload")).put("op", "u");
            ((Map<String, Object>) intercambio.get("payload")).put("almacen", entity.getClass().getSimpleName());
            ((Map<String, Object>) intercambio.get("payload")).put("after", ConversionUtils.convertToMap(updated));*/
            EventArch eventArch = new EventArch(UUID.randomUUID().toString(), EventArch.EVENT_TYPE_UPDATE, updated);
            commandPublisher.publish(EventArch.EVENT_TOPIC, /*ConversionUtils.map2Jsonstring(intercambio)*/eventArch);
        }
        return updated;
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
        // cqrs artesanal
        /*Map<String, Object> intercambio = new HashMap<>();
        intercambio.put("payload", new HashMap<String, Object>());
        ((Map<String, Object>) intercambio.get("payload")).put("op", "d");
        ((Map<String, Object>) intercambio.get("payload")).put("almacen", entity.getClass().getSimpleName());
        ((Map<String, Object>) intercambio.get("payload")).put("before", ConversionUtils.convertToMap(entity));*/
        EventArch eventArch = new EventArch(UUID.randomUUID().toString(), EventArch.EVENT_TYPE_DELETE, entity);
        commandPublisher.publish(EventArch.EVENT_TOPIC, /*ConversionUtils.map2Jsonstring(intercambio)*/eventArch);
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
