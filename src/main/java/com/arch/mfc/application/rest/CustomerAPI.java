package com.arch.mfc.application.rest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arch.mfc.infra.domain.BaseEntity;
import com.arch.mfc.infra.inputport.GenericInputPort;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arch.mfc.infra.inputport.CQRSMessageBrokerInputPort;

@RestController
@RequestMapping(value = "customer")
public class CustomerAPI {

    @Autowired
    GenericInputPort customerInputPort;

    @Autowired
    CQRSMessageBrokerInputPort messageBrokerInputPort;

    @Autowired
    JdbcTemplate jdbcTemplate;    

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public BaseEntity create(@RequestParam String name, @RequestParam String country ) {
        Map<String, Object> mapObj = new HashMap<>();
        mapObj.put("name", name);
        mapObj.put("country", country);
        return customerInputPort.create(mapObj);
    }

    @PostMapping(value = "getallWithoutCQRS", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<BaseEntity> getAllWithoutCQRS() {
        return customerInputPort.getAll();
    }

    @PostMapping(value = "getallCustomerCQRS", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,Object>> getallNonCQRS() {
        return messageBrokerInputPort.getAll( "customer" );
    }

    @PostMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public BaseEntity get( @RequestParam Long customerId ) {
        return customerInputPort.getById(customerId);
    }


}
