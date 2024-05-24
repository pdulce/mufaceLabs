package com.arch.mfc.infra.outputport;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public interface PairKeyValueRepositoryInterface {

    void save( Map<String,Object> reg, String almacen );

    void delete( String id, String almacen );

    Map<String,Object> getById( String id, String almacen );

    List<Map<String,Object>> getAll( String almacen );
}
