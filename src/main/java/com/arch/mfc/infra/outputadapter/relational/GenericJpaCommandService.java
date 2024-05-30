package com.arch.mfc.infra.outputadapter.relational;

import com.arch.mfc.infra.events.EventArch;
import com.arch.mfc.infra.outputport.GenericCommandPort;
import com.arch.mfc.infra.events.adapter.CommandPublisher;
import com.arch.mfc.infra.utils.ConversionUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional
public class GenericJpaCommandService<T> implements GenericCommandPort<T> {

    @Autowired
    CommandPublisher commandPublisher;
    @Autowired
    protected JpaRepository<T, Long> repository;

    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericJpaCommandService() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public final T save(T entity) {
        T saved = repository.save(entity);
        if (saved != null) {
            /*** Mando el evento al bus para que los recojan los dos consumers:
             *  - el responsable del dominio de eventos que persiste en MongoDB (patrón Event-Sourcing)
             *  - el responsable del dominio de consultas que persiste en Redis (patrón CQRS)
             */
            EventArch eventArch = new EventArch(entity.getClass().getSimpleName(),
                    ConversionUtils.convertToMap(saved).get("id").toString(),
                    EventArch.EVENT_TYPE_CREATE, entity);
            commandPublisher.publish(EventArch.EVENT_TOPIC, eventArch);
        }
        return saved;
    }

    @Override
    public final T update(T entity) {
        T updated =  repository.save(entity);
        if (updated != null) {
            // cqrs artesanal
            /*Map<String, Object> intercambio = new HashMap<>();
            intercambio.put("payload", new HashMap<String, Object>());
            ((Map<String, Object>) intercambio.get("payload")).put("op", "u");
            ((Map<String, Object>) intercambio.get("payload")).put("almacen", entity.getClass().getSimpleName());
            ((Map<String, Object>) intercambio.get("payload")).put("after", ConversionUtils.convertToMap(updated));*/
            EventArch eventArch = new EventArch(entity.getClass().getSimpleName(),
                    ConversionUtils.convertToMap(entity).get("id").toString(),
                    EventArch.EVENT_TYPE_UPDATE, updated);
            commandPublisher.publish(EventArch.EVENT_TOPIC, eventArch);
        }
        return updated;
    }

    @Override
    public final void delete(T entity) {
        repository.delete(entity);
        EventArch eventArch = new EventArch(entity.getClass().getSimpleName(),
                ConversionUtils.convertToMap(entity).get("id").toString(),
                EventArch.EVENT_TYPE_DELETE, entity);
        commandPublisher.publish(EventArch.EVENT_TOPIC, eventArch);
    }

    @Override
    public final T findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public final List<T> findAll() {
        return repository.findAll().stream().toList();
    }

    /** método generíco para buscar dentro de cualquier campo de un entidad T **/

    public final List<T> getByFieldvalue(String fieldName, Object fieldValue) {

        try {
            T instance = entityClass.getDeclaredConstructor().newInstance();
            // llenamos esta instancia con el field y value recibidos
            Field field = entityClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, fieldValue);
            return repository.findAll(Example.of(instance));
        } catch (Throwable exc) {
            return null;
        }
    }

}
