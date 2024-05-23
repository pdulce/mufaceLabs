package com.arch.mfc.infra.outputadapter.nonrelational;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.arch.mfc.infra.outputport.QueryRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;

import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.stereotype.Service;


@RedisHash
@Service
public class RepositoryRedisImpl<T> implements QueryRepositoryInterface<T> {

    @Autowired
    protected RedisTemplate<String, String> redisTemplate;


    @Override
    public void save(Map<String, Object> reg, Class<T> clazz) {
        redisTemplate.opsForHash().put(
                getHashFromClass( clazz ),
                reg.get("id"),
                ConversionUtils.map2Jsonstring( reg )
        );
    }

    @Override
    public void delete(String id, Class<T> clazz) {
        redisTemplate.opsForHash().delete(
            getHashFromClass( clazz ), 
            id
        );
    }

    @Override
    public Map<String, Object> getById(String id, Class<T> clazz) {
        return ConversionUtils.jsonstring2Map(
            (String) redisTemplate.opsForHash().get( 
                getHashFromClass( clazz ), 
                id 
            )
        );
    }

    @Override
    public List<Map<String, Object>> getAll(Class<T> clazz) {
        return redisTemplate.opsForHash()
            .values( getHashFromClass( clazz ) )
            .stream()
            .map( el -> ConversionUtils.jsonstring2Map( (String) el ) )
            .collect( Collectors.toList() );
    }

    protected String getHashFromClass( Class<T> clazz ) {
        return clazz.getName().replace('.', '_');
    }



}
