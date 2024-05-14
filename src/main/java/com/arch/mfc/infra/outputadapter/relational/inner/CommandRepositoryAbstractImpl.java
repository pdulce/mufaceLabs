package com.arch.mfc.infra.outputadapter.relational.inner;

import com.arch.mfc.infra.outputport.CommandRepositoryInterface;
import jakarta.persistence.Entity;
import org.apache.poi.ss.formula.eval.NotImplementedFunctionException;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class CommandRepositoryAbstractImpl implements CommandRepositoryInterface {

    @Override
    public void flush() {
        flush();
    }

    @Override
    public <S extends Entity> S saveAndFlush(S entity) {
        return saveAndFlush(entity);
    }

    @Override
    public <S extends Entity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Entity> entities) {
        deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAllInBatch();
    }

    @Override
    public Entity getOne(Long aLong) {
        return getOne(aLong);
    }

    @Override
    public Entity getById(Long aLong) {
        return getById(aLong);
    }

    @Override
    public Entity getReferenceById(Long aLong) {
        return getReferenceById(aLong);
    }

    @Override
    public <S extends Entity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Entity> List<S> findAll(Example<S> example) {
        return findAll(example);
    }

    @Override
    public <S extends Entity> List<S> findAll(Example<S> example, Sort sort) {
        return findAll(example, sort);
    }

    @Override
    public <S extends Entity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return findAll(example, pageable);
    }

    @Override
    public <S extends Entity> long count(Example<S> example) {
        return count(example);
    }

    @Override
    public <S extends Entity> boolean exists(Example<S> example) {
        return exists(example);
    }

    @Override
    public <S extends Entity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new NotImplementedFunctionException("");
    }

    @Override
    public <S extends Entity> S save(S entity) {
        return save(entity);
    }

    @Override
    public <S extends Entity> List<S> saveAll(Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public Optional<Entity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return existsById(aLong);
    }

    @Override
    public List<Entity> findAll() {
        return findAll();
    }

    @Override
    public List<Entity> findAllById(Iterable<Long> longs) {
        return findAllById(longs);
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        deleteById(aLong);
    }

    @Override
    public void delete(Entity entity) {
        delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Entity> entities) {
        deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        deleteAll();
    }

    @Override
    public List<Entity> findAll(Sort sort) {
        return findAll(sort);
    }

    @Override
    public Page<Entity> findAll(Pageable pageable) {
        return findAll(pageable);
    }


}
