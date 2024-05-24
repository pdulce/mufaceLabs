package com.arch.mfc.infra.outputadapter.nonrelational;

import com.arch.mfc.infra.outputport.DocumentRepositoryInterface;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MongoImpl<T> implements DocumentRepositoryInterface<T> {

    protected MongoRepository<T, String> getRepository() {
        return null;
    }

    @Override
    public void save(Map<String, Object> reg, Class<T> clazz) {
        String json = ConversionUtils.map2Jsonstring(reg);
        T order = ConversionUtils.jsonStringToObject(json, clazz);
        getRepository().save(order);
    }

    @Override
    public void delete(String id) {
        getRepository().deleteById(id);
    }

    @Override
    public Map<String, Object> getById(String id, Class<T> clazz) {
        Optional<T> order = getRepository().findById(id);
        return order.map(ConversionUtils::objectToMap).orElse(null);
    }

    @Override
    public List<Map<String, Object>> getAll(Class<T> clazz) {
        List<T> orders = getRepository().findAll();
        return orders.stream().map(ConversionUtils::objectToMap).collect(Collectors.toList());
    }

}
