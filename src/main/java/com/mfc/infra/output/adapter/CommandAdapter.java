package com.mfc.infra.output.adapter;

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
import java.util.List;

@Transactional
public abstract class CommandAdapter<T> implements CommandPort<T> {

    Logger logger = LoggerFactory.getLogger(CommandAdapter.class);
    @Autowired
    CommandEventPublisherPort commandEventPublisherPort;
    @Autowired
    protected JpaRepository<T, Long> repository;

    @Override
    public T insert(T entity) {
        T saved = this.repository.save(entity);
        if (saved != null) {
            /*** Mando el evento al bus para que los recojan los dos consumers:
             *  - consumer responsable del dominio de eventos que persiste en MongoDB (patrón Event-Sourcing)
             *  - consumer responsable del dominio de consultas que persiste en Redis (patrón CQRS)
             */
            Event eventArch = new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                    ConversionUtils.convertToMap(saved).get("id").toString(),
                    Event.EVENT_TYPE_CREATE, entity);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        }
        return saved;
    }

    @Override
    public T update(T entity) throws NotExistException {
        T updated =  this.repository.save(entity);
        if (updated != null) {
            Event eventArch = new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                    ConversionUtils.convertToMap(entity).get("id").toString(),
                    Event.EVENT_TYPE_UPDATE, updated);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        }
        return updated;
    }

    @Override
    public void delete(T entity) throws NotExistException {
        if (this.repository.findById(Long.valueOf(ConversionUtils.convertToMap(entity).get("id").toString()))
                .isPresent()) {
            this.repository.delete(entity);
            Event eventArch = new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                    ConversionUtils.convertToMap(entity).get("id").toString(),
                    Event.EVENT_TYPE_DELETE, entity);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        } else {
            throw new NotExistException();
        }
    }

    @Override
    public void deleteAll() {
        findAll().forEach((record) -> {
            Event eventArch = new Event(getDocumentEntityClassname(), "author", "application-Id-2929",
                    ConversionUtils.convertToMap(record).get("id").toString(),
                    Event.EVENT_TYPE_DELETE, record);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        });
        this.repository.deleteAll();
    }

    @Override
    public T findById(Long id) throws NotExistException {
        if (this.repository.findById(id).isPresent()) {
           return this.repository.findById(id).get();
        }
        throw new NotExistException();
    }

    @Override
    public List<T> findAll() {
        return this.repository.findAll().stream().toList();
    }

    /** método generíco para buscar dentro de cualquier campo de un entidad T **/

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
