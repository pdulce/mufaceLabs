package com.arch.mfc.infra.outputadapter.relational;

import com.arch.mfc.infra.domain.BaseEntity;
import com.arch.mfc.infra.outputport.CommandRepositoryInterface;
import org.apache.poi.ss.formula.eval.NotImplementedFunctionException;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CommandRepositoryImpl implements CommandRepositoryInterface {

    /*** architecture adhoc methods ***/

    public BaseEntity grabar(BaseEntity entity) {
        return save(entity);
    }

    public List<BaseEntity> buscarTodos() {
        return findAll();
    }

    public BaseEntity buscarPorId(Long id) {
        return findById(id).get();
    }

    public List<BaseEntity> buscarPorCriterios(BaseEntity entity) {
        Example<BaseEntity> criteria = new Example<BaseEntity>() {
            @Override
            public BaseEntity getProbe() {
                return this.getProbe();
            }

            @Override
            public ExampleMatcher getMatcher() {
                return this.getMatcher();
            }

            @Override
            public Class<BaseEntity> getProbeType() {
                return Example.super.getProbeType();
            }
        };
        return findAll(criteria);
    }

    public BaseEntity borrarPorId(Long id) {
        BaseEntity entity = getById(id);
        if (entity != null) {
            deleteById(id);
            return entity;
        }
        return null;
    }

    @Override
    public void flush() {
        flush();
    }

    @Override
    public <S extends BaseEntity> S saveAndFlush(S entity) {
        return saveAndFlush(entity);
    }

    @Override
    public <S extends BaseEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<BaseEntity> entities) {
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
    public BaseEntity getOne(Long aLong) {
        return getOne(aLong);
    }

    @Override
    public BaseEntity getById(Long aLong) {
        return getById(aLong);
    }

    @Override
    public BaseEntity getReferenceById(Long aLong) {
        return getReferenceById(aLong);
    }

    @Override
    public <S extends BaseEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends BaseEntity> List<S> findAll(Example<S> example) {
        return findAll(example);
    }

    @Override
    public <S extends BaseEntity> List<S> findAll(Example<S> example, Sort sort) {
        return findAll(example, sort);
    }

    @Override
    public <S extends BaseEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return findAll(example, pageable);
    }

    @Override
    public <S extends BaseEntity> long count(Example<S> example) {
        return count(example);
    }

    @Override
    public <S extends BaseEntity> boolean exists(Example<S> example) {
        return exists(example);
    }

    @Override
    public <S extends BaseEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new NotImplementedFunctionException("");
    }

    @Override
    public <S extends BaseEntity> S save(S entity) {
        return save(entity);
    }

    @Override
    public <S extends BaseEntity> List<S> saveAll(Iterable<S> entities) {
        return saveAll(entities);
    }

    @Override
    public Optional<BaseEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return existsById(aLong);
    }

    @Override
    public List<BaseEntity> findAll() {
        return findAll();
    }

    @Override
    public List<BaseEntity> findAllById(Iterable<Long> longs) {
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
    public void delete(BaseEntity entity) {
        delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends BaseEntity> entities) {
        deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        deleteAll();
    }

    @Override
    public List<BaseEntity> findAll(Sort sort) {
        return findAll(sort);
    }

    @Override
    public Page<BaseEntity> findAll(Pageable pageable) {
        return findAll(pageable);
    }


}
