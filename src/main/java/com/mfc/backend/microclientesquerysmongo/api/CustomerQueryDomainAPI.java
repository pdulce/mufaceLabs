package com.mfc.backend.microclientesquerysmongo.api;


import com.mfc.backend.microclientesquerysmongo.api.dto.CustomerDocumentDTO;
import com.mfc.backend.microclientesquerysmongo.api.usecases.QueriesOptimizedUseCase;
import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.infra.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "customerquery")
public class CustomerQueryDomainAPI extends BaseRestController {

    @Autowired(required=false)
    QueriesOptimizedUseCase queriesOptimizedUseCase;


    /*** CONSULTAS CONTRA EL DOMINIO DE QUERIES ***/

    @GetMapping(value = "getFromQueryStoreMongoById", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerDocumentDTO getFromQueryStoreMongoById(@RequestParam String customerId) {
        CustomerDocument customerDocument = this.queriesOptimizedUseCase.ejecutar(customerId);
        return new CustomerDocumentDTO(customerDocument);
    }

    @GetMapping(value = "getAllFromQueryStoreMongo", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDocumentDTO> getAllFromQueryStoreMongo() {
        List<CustomerDocumentDTO> lista = new ArrayList<>();
        this.queriesOptimizedUseCase.ejecutar().forEach((customerDocument -> {
            lista.add(new CustomerDocumentDTO(customerDocument));
        }));
        return lista;
    }



}