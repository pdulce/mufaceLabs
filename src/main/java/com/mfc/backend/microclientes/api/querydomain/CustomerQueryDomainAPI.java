package com.mfc.backend.microclientes.api.querydomain;


import com.mfc.backend.microclientes.domain.model.query.CustomerDocument;
import com.mfc.infra.controller.BaseRestController;
import com.mfc.infra.input.adapter.QueryInputConsumerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "customerquery")
public class CustomerQueryDomainAPI extends BaseRestController {

    @Autowired(required=false)
    QueryInputConsumerAdapter<CustomerDocument> customerQueryService;


    /*** CONSULTAS CONTRA EL DOMINIO DE QUERIES ***/

    @GetMapping(value = "getFromQueryStoreMongoById", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getFromQueryStoreMongoById(@RequestParam String customerId) {
        return this.customerQueryService.findById(customerId);
    }

    @GetMapping(value = "getAllFromQueryStoreMongo", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getAllFromQueryStoreMongo() {
        return this.customerQueryService.findAll();
    }




}
