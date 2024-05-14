package com.arch.mfc.infra.outputadapter.nonrelational;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.arch.mfc.infra.outputport.QueryRepositoryInterface;
import com.arch.mfc.infra.utils.ConversionUtils;

@Component
public class RedisQueryRepositoryImpl implements QueryRepositoryInterface {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(Map<String, Object> reg, Class<?> clazz) {   
        redisTemplate.opsForHash().put(
            getHashFromClass( clazz ), 
            reg.get("id"), 
            ConversionUtils.map2Jsonstring( reg )
        );

        if ( clazz.equals( Order.class ) ) addOrderToCustomer( reg, true );
    }

    @Override
    public void delete(String id, Class<?> clazz) {
        if ( clazz.equals( Order.class ) ) addOrderToCustomer( id, false );

        redisTemplate.opsForHash().delete( 
            getHashFromClass( clazz ), 
            id
        );
    }

    @Override
    public Map<String, Object> getById(String id, Class<?> clazz) {
        return ConversionUtils.jsonstring2Map(
            (String) redisTemplate.opsForHash().get( 
                getHashFromClass( clazz ), 
                id 
            )
        );
    }

    @Override
    public List<Map<String, Object>> getAll(Class<?> clazz) {
        return redisTemplate.opsForHash()
            .values( getHashFromClass( clazz ) )
            .stream()
            .map( el -> ConversionUtils.jsonstring2Map( (String) el ) )
            .collect( Collectors.toList() );
    }

    private void addOrderToCustomer( String orderId, boolean appendOrder ) {
        addOrderToCustomer(
            getById( orderId, Order.class),
            appendOrder
        );
    }

    private void addOrderToCustomer( Map<String, Object> order, boolean appendOrder ) {
        String customerId = (String) order.get("customerid");
        if ( customerId == null ) return;

        Map<String, Object> customer = ConversionUtils.jsonstring2Map( 
            (String) redisTemplate.opsForHash().get( 
                getHashFromClass( Customer.class ),
                customerId 
            ) 
        );
        
        List<Map<String, Object>> orders = (List<Map<String, Object>>) customer.get("orders");
        if ( orders == null ) orders = new ArrayList<>();

        orders = orders.stream()
            .filter( el -> !((Map<String, Object>) el).get("id").equals( order.get("id") ) )
            .collect( Collectors.toList() );

        if ( appendOrder ) orders.add( order );

        customer.put("orders", orders);

        redisTemplate.opsForHash().put(
            getHashFromClass( Customer.class ), 
            customerId, 
            ConversionUtils.map2Jsonstring( customer )
        );
    }

    private String getHashFromClass( Class<?> clazz ) {
        return clazz.getName().replace('.', '_');
    }

}
