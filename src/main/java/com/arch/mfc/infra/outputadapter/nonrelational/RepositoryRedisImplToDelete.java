package com.arch.mfc.infra.outputadapter.nonrelational;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.arch.mfc.infra.outputport.PairKeyValueRepositoryInterface;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;

import com.arch.mfc.infra.utils.ConversionUtils;
import org.springframework.stereotype.Service;


@RedisHash
@Service
public class RepositoryRedisImplToDelete implements PairKeyValueRepositoryInterface {

    //@Autowired
    protected RedisTemplate<String, String> redisTemplate;


    @Override
    public void save(Map<String, Object> reg, String almacen) {
        redisTemplate.opsForHash().put(almacen, reg.get("id"), ConversionUtils.map2Jsonstring( reg )
        );
    }

    @Override
    public void delete(String id, String almacen) {
        redisTemplate.opsForHash().delete(almacen, id);
    }

    @Override
    public Map<String, Object> getById(String id, String almacen) {
        return ConversionUtils.jsonstring2Map(
            (String) redisTemplate.opsForHash().get(almacen, id)
        );
    }

    @Override
    public List<Map<String, Object>> getAll(String almacen) {
        return redisTemplate.opsForHash()
            .values( almacen )
            .stream()
            .map( el -> ConversionUtils.jsonstring2Map( (String) el ) )
            .collect( Collectors.toList() );
    }


}
