package com.arch.mfc.infra.outputadapter.relational;

import com.arch.mfc.infra.event.commands.CommandGeneric;
import com.arch.mfc.infra.inputport.GenericInputPort;
import com.arch.mfc.infra.message.CommandProducerServiceBroker;
import jakarta.transaction.Transactional;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.List;

@Transactional
public class GenericJpaService<T> implements GenericInputPort<T> {

    @Autowired
    CommandProducerServiceBroker commandProducerServiceBroker;
    @Autowired
    CommandGateway commandGateway;

    @Autowired
    protected JpaRepository<T, Long> repository;

    private Class<T> entityClass;

    public GenericJpaService() {}
    public GenericJpaService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        T saved = repository.save(entity);
        if (saved != null) {
            // aplicamos el patrón CQRS vía AXON
            String idGot = String.valueOf(getId(saved));
            commandGateway.send(new CommandGeneric("create_".concat(idGot), saved));
            // cqrs casero
            commandProducerServiceBroker.sendMessage("topicCQRS", "create_ ".concat(idGot));
        }
        return saved;
    }

    @Override
    public T update(T entity) {
        T updated =  repository.save(entity);
        if (updated != null) {
            // aplicamos el patrón CQRS vía AXON
            String idGot = String.valueOf(getId(updated));
            commandGateway.send(new CommandGeneric("update_".concat(idGot), updated));
            // cqrs casero
            commandProducerServiceBroker.sendMessage("topicCQRS", "update_ ".concat(idGot));
        }
        return updated;
    }

    @Override
    public void delete(T entity) {
        repository.delete(entity);
        // aplicamos el patrón CQRS vía AXON
        String idGot = String.valueOf(getId(entity));
        commandGateway.send(new CommandGeneric("delete_".concat(idGot), entity));
        // cqrs casero
        commandProducerServiceBroker.sendMessage("topicCQRS", "delete_ ".concat(idGot));
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
