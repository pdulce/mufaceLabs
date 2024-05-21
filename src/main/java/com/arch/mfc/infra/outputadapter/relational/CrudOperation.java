package com.arch.mfc.infra.outputadapter.relational;

import com.arch.mfc.infra.domain.BaseEntity;
import com.arch.mfc.infra.event.commands.CommandGeneric;
import com.arch.mfc.infra.inputport.GenericInputPort;
import com.arch.mfc.infra.message.CommandProducerServiceBroker;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public abstract class CrudOperation implements GenericInputPort {

    @Autowired
    CommandProducerServiceBroker commandProducerServiceBroker;
    @Autowired
    CommandGateway commandGateway;
    protected abstract JpaRepository<BaseEntity, Long> getJPaRepository();

    @Override
    public BaseEntity create(BaseEntity baseEntity) {
        baseEntity.setId(UUID.randomUUID().getMostSignificantBits());
        BaseEntity saved = getJPaRepository().save(baseEntity);
        if (saved != null) {
            // aplicamos el patrón CQRS vía AXON
            commandGateway.send(new CommandGeneric("create_".concat(String.valueOf(saved.getId())), saved));
            // cqrs casero
            commandProducerServiceBroker.sendMessage("topicMyCQRS", "create_ " + saved.getId());
        }

        return saved;
    }

    @Override
    public BaseEntity delete(BaseEntity baseEntity) {
        BaseEntity deleted = getJPaRepository().save(baseEntity);
        if (deleted != null) {
            // aplicamos el patrón CQRS vía AXON
            commandGateway.send(new CommandGeneric("delete_".concat(String.valueOf(deleted.getId())), deleted));
            // cqrs casero
            commandProducerServiceBroker.sendMessage("topicMyCQRS", "delete_ " + deleted.getId());
        }
        return deleted;
    }

    @Override
    public BaseEntity update(BaseEntity baseEntity) {
        BaseEntity updated = getJPaRepository().save(baseEntity);
        if (updated != null) {
            // aplicamos el patrón CQRS vía AXON
            commandGateway.send(new CommandGeneric("update_".concat(String.valueOf(updated.getId())), updated));
            // cqrs casero
            commandProducerServiceBroker.sendMessage("topicMyCQRS", "update_ " + updated.getId());
        }
        return updated;
    }

    @Override
    public BaseEntity getById(Long id) {
        return getJPaRepository().findById(id).get();
    }

    @Override
    public List<BaseEntity> getAll() {
        return getJPaRepository().findAll();
    }

    @Override
    public Page<BaseEntity> getAllPaged(Pageable pageable) { return getJPaRepository().findAll(pageable); }


}