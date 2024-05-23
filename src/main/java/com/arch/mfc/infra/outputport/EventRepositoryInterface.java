package com.arch.mfc.infra.outputport;

import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Document
public interface EventRepositoryInterface<T> {

    void save(Map<String, Object> reg, Class<T> clazz);

    void delete(String id);


    Map<String, Object> getById(String id, Class<T> clazz) ;

    List<Map<String, Object>> getAll(Class<T> clazz);

}
