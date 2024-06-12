package com.mfc.backend.microclientes.api.querydomain;


import com.mfc.backend.microclientes.api.usecases.QueriesOptimizedUseCase;
import com.mfc.backend.microclientes.domain.model.query.CustomerDocument;
import com.mfc.infra.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "customerquery")
public class CustomerQueryDomainAPI extends BaseRestController {

    @Autowired(required=false)
    QueriesOptimizedUseCase queriesOptimizedUseCase;


    /*** CONSULTAS CONTRA EL DOMINIO DE QUERIES ***/

    @GetMapping(value = "getFromQueryStoreMongoById", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerDocument getFromQueryStoreMongoById(@RequestParam String customerId) {
        return this.queriesOptimizedUseCase.ejecutar(customerId);
    }

    @GetMapping(value = "getAllFromQueryStoreMongo", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDocument> getAllFromQueryStoreMongo() {
        return this.queriesOptimizedUseCase.ejecutar();
    }



}
