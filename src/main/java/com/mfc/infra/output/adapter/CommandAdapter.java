package com.mfc.infra.output.adapter;

import com.mfc.infra.configuration.EventBrokerProperties;
import com.mfc.infra.event.Event;
import com.mfc.infra.exceptions.NotExistException;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import com.mfc.infra.output.port.CommandPort;
import com.mfc.infra.utils.ConversionUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Transactional
public class CommandAdapter<T> implements CommandPort<T> {
    Logger logger = LoggerFactory.getLogger(CommandAdapter.class);
    @Autowired
    EventBrokerProperties eventBrokerProperties;
    @Autowired
    CommandEventPublisherPort commandEventPublisherPort;
    @Autowired
    protected JpaRepository<T, Long> repository;

    public final String getDocumentEntityClassname() {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        return entityClass.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T insert(T entity) {
        T saved = this.repository.save(entity);
        if (saved != null && eventBrokerProperties.isActive()) {
            /*** Mando el evento al bus para que los recojan los dos consumers:
             *  - consumer responsable del dominio de eventos que persiste en MongoDB (patrón Event-Sourcing)
             *  - consumer responsable del dominio de consultas que persiste en Redis (patrón CQRS)
             */
            Event eventArch = new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                    ConversionUtils.convertToMap(saved).get("id").toString(),
                    Event.EVENT_TYPE_CREATE, saved);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        }
        return saved;
    }


    @SuppressWarnings("unchecked")
    @Override
    public T update(T entity) {
        Long id = Long.valueOf(ConversionUtils.convertToMap(entity).get("id").toString());
        if (!this.repository.findById(id).isPresent()) {
            NotExistException e = new NotExistException();
            e.setMsgError("entity with id: " + String.valueOf(id) + " not found");
            RuntimeException exc = new RuntimeException(e);
            throw exc;
        }
        T updated =  this.repository.save(entity);
        if (updated != null && eventBrokerProperties.isActive()) {
            Event eventArch = new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                    ConversionUtils.convertToMap(entity).get("id").toString(),
                    Event.EVENT_TYPE_UPDATE, updated);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        }
        return updated;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(T entity) {
        Long id = Long.valueOf(ConversionUtils.convertToMap(entity).get("id").toString());
        if (!this.repository.findById(id).isPresent()) {
            NotExistException e = new NotExistException();
            e.setMsgError("entity with id: " + String.valueOf(id) + " not found");
            RuntimeException exc = new RuntimeException(e);
            throw exc;
        }
        this.repository.delete(entity);
        if (eventBrokerProperties.isActive()) {
            Event eventArch = new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                    ConversionUtils.convertToMap(entity).get("id").toString(),
                    Event.EVENT_TYPE_DELETE, entity);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteAllList(List<T> entities) {
        entities.forEach((record) -> {
            this.delete(record);
            if (eventBrokerProperties.isActive()) {
                Event eventArch = new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                        ConversionUtils.convertToMap(record).get("id").toString(),
                        Event.EVENT_TYPE_DELETE, record);
                commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void deleteAll() {
        List<Event> events = new ArrayList<>();
        findAll().forEach((record) -> {
            events.add(new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                    ConversionUtils.convertToMap(record).get("id").toString(),
                    Event.EVENT_TYPE_DELETE, record));
        });
        this.repository.deleteAll();
        if (eventBrokerProperties.isActive()) {
            events.forEach((event) -> {
                commandEventPublisherPort.publish(Event.EVENT_TOPIC, event);
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(Long id) {
        if (this.repository.findById(id).isPresent()) {
           return this.repository.findById(id).get();
        }
        NotExistException e = new NotExistException();
        e.setMsgError("id: " + id);
        RuntimeException exc = new RuntimeException(e);
        throw exc;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return this.repository.findAll().stream().toList();
    }

    /** método generíco para buscar dentro de cualquier campo de un entidad T **/

    @SuppressWarnings("unchecked")
    public List<T> findAllByFieldvalue(String fieldName, Object fieldValue) {

        try {
            Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass())
                    .getActualTypeArguments()[0];
            T instance = entityClass.getDeclaredConstructor().newInstance();
            // llenamos esta instancia con el field y value recibidos
            Field field = entityClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, fieldValue);
            return this.repository.findAll(Example.of(instance));
        } catch (Throwable exc) {
            return null;
        }
    }

}
