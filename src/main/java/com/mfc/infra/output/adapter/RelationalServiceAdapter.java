package com.mfc.infra.output.adapter;

import com.mfc.infra.configuration.ConfigProperties;
import com.mfc.infra.domain.DTOConverter;
import com.mfc.infra.event.Event;
import com.mfc.infra.exceptions.NotExistException;
import com.mfc.infra.output.port.CommandEventPublisherPort;
import com.mfc.infra.output.port.RelationalServicePort;
import com.mfc.infra.output.port.GenericRepositoryPort;
import com.mfc.infra.utils.ConversionUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@Transactional
public abstract class RelationalServiceAdapter<T, D, ID> implements RelationalServicePort<T, D, ID> {
    Logger logger = LoggerFactory.getLogger(RelationalServiceAdapter.class);
    @Autowired
    ConfigProperties configProperties;

    @Autowired(required = false)
    CommandEventPublisherPort commandEventPublisherPort;

    protected abstract GenericRepositoryPort<T, ID> getRepository();

    public String getDocumentEntityClassname() {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        return entityClass.getSimpleName().toLowerCase();
    }

    protected Class<T> getClassOfEntity() {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        return entityClass;
    }

    protected Class<D> getClassOfDTO() {
        Class<D> entityClass = (Class<D>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
        return entityClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public D crear(D entityDto) {
        T saved = this.getRepository().save(DTOConverter.convertToEntity(entityDto, getClassOfEntity()));
        if (saved != null && configProperties.isEventBrokerActive()) {
            /*** Mando el evento al bus para que los recojan los dos consumers:
             *  - consumer responsable del dominio de eventos que persiste en MongoDB (patrón Event-Sourcing)
             *  - consumer responsable del dominio de consultas que persiste en Redis (patrón CQRS)
             */
            Event eventArch = new Event(getDocumentEntityClassname(), "author",
                    configProperties.getApplicationId(),
                    ConversionUtils.convertToMap(saved).get("id").toString(),
                    Event.EVENT_TYPE_CREATE, saved);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        }
        return DTOConverter.convertToDTO(saved, getClassOfDTO());
    }


    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public D actualizar(D entityDto) {
        T entity = DTOConverter.convertToEntity(entityDto, getClassOfEntity());
        ID id = (ID) ConversionUtils.convertToMap(entity).get("id");
        if (!this.getRepository().findById(id).isPresent()) {
            NotExistException e = new NotExistException();
            e.setMsgError("entity with id: " + String.valueOf(id) + " not found");
            RuntimeException exc = new RuntimeException(e);
            throw exc;
        }
        T updated =  this.getRepository().save(entity);
        if (updated != null && configProperties.isEventBrokerActive()) {
            Event eventArch = new Event(getDocumentEntityClassname(), "author",
                    configProperties.getApplicationId(),
                    ConversionUtils.convertToMap(entity).get("id").toString(),
                    Event.EVENT_TYPE_UPDATE, updated);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        }
        return DTOConverter.convertToDTO(updated, getClassOfDTO());
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void borrar(D entityDto) {
        T entity = DTOConverter.convertToEntity(entityDto, getClassOfEntity());
        ID id = (ID) ConversionUtils.convertToMap(entity).get("id");
        if (!this.getRepository().findById(id).isPresent()) {
            NotExistException e = new NotExistException();
            e.setMsgError("entity with id: " + String.valueOf(id) + " not found");
            RuntimeException exc = new RuntimeException(e);
            throw exc;
        }
        this.getRepository().delete(entity);
        if (configProperties.isEventBrokerActive()) {
            Event eventArch = new Event(getDocumentEntityClassname(), "author",
                    configProperties.getApplicationId(),
                    ConversionUtils.convertToMap(entity).get("id").toString(),
                    Event.EVENT_TYPE_DELETE, entity);
            commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void borrar(List<D> entities) {
        entities.forEach((record) -> {
            this.borrar(record);
            if (configProperties.isEventBrokerActive()) {
                Event eventArch = new Event(getDocumentEntityClassname(), "author",
                        configProperties.getApplicationId(),
                        ConversionUtils.convertToMap(record).get("id").toString(),
                        Event.EVENT_TYPE_DELETE, record);
                commandEventPublisherPort.publish(Event.EVENT_TOPIC, eventArch);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void borrar() {
        List<Event> events = new ArrayList<>();
        buscarTodos().forEach((record) -> {
            events.add(new Event(getDocumentEntityClassname(), "author",
                    configProperties.getApplicationId(),
                    ConversionUtils.convertToMap(record).get("id").toString(),
                    Event.EVENT_TYPE_DELETE, record));
        });
        this.getRepository().deleteAll();
        if (configProperties.isEventBrokerActive()) {
            events.forEach((event) -> {
                commandEventPublisherPort.publish(Event.EVENT_TOPIC, event);
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public D buscarPorId(ID id) {
        if (!this.getRepository().findById(id).isPresent()) {
            logger.info("buscarPorId no localizó el id: " + id);
            NotExistException e = new NotExistException();
            e.setMsgError("id: " + id);
            RuntimeException exc = new RuntimeException(e);
            throw exc;
        }
        return DTOConverter.convertToDTO(this.getRepository().findById(id).get(), getClassOfDTO());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<D> buscarTodos() {
        List<D> resultado = new ArrayList<>();
        this.getRepository().findAll().stream().toList().forEach((entity) -> {
            resultado.add(DTOConverter.convertToDTO(entity, getClassOfDTO()));
        });
        return resultado;
    }

    /** método generíco para buscar dentro de cualquier campo de un entidad T **/

    @SuppressWarnings("unchecked")
    public List<D> buscarPorCampoValor(String fieldName, Object fieldValue) {

        try {
            Class<T> entityClass = getClassOfEntity();
            T instance = entityClass.getDeclaredConstructor().newInstance();
            // llenamos esta instancia con el field y value recibidos
            Field field = entityClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, fieldValue);

            List<D> resultado = new ArrayList<>();
            this.getRepository().findAll(Example.of(instance)).forEach((entity) -> {
                resultado.add(DTOConverter.convertToDTO(entity, getClassOfDTO()));
            });
            return resultado;
        } catch (Throwable exc1) {
            logger.error("Error in buscarPorCampoValor method: ", exc1.getCause());
            NotExistException e = new NotExistException();
            e.setMsgError("fieldName: " + fieldName + " not exists for this Entity class");
            RuntimeException exc = new RuntimeException(e);
            throw exc;
        }
    }

}
