package com.arch.mfc.infra.outputadapter.nonrelational;

import com.arch.mfc.infra.outputport.QueryRepositoryInterface;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import com.arch.mfc.infra.utils.ConversionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MongoImpl<T> implements QueryRepositoryInterface<T> {

    protected MongoRepository<T, String> repository;

    public MongoImpl() {}

    public MongoImpl(MongoRepository<T, String> repository) {

        this.repository = repository;
    }

    @Override
    public void save(Map<String, Object> reg, Class<T> clazz) {
        String json = ConversionUtils.map2Jsonstring(reg);
        T order = ConversionUtils.jsonStringToObject(json, clazz);
        repository.save(order);
    }

    @Override
    public void delete(String id, Class<T> clazz) {
        repository.deleteById(id);
    }

    @Override
    public Map<String, Object> getById(String id, Class<T> clazz) {
        Optional<T> order = repository.findById(id);
        return order.map(ConversionUtils::objectToMap).orElse(null);
    }

    @Override
    public List<Map<String, Object>> getAll(Class<T> clazz) {
        List<T> orders = repository.findAll();
        return orders.stream().map(ConversionUtils::objectToMap).collect(Collectors.toList());
    }

}
