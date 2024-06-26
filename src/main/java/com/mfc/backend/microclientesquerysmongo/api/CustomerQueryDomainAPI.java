package com.mfc.backend.microclientesquerysmongo.api;


import com.mfc.backend.microclientesquerysmongo.api.dto.CustomerDocumentDTO;
import com.mfc.backend.microclientesquerysmongo.api.usecases.QueriesOptimizedUseCase;
import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.infra.controller.ArqBaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "customersquery")
public class CustomerQueryDomainAPI extends ArqBaseRestController {
    @Autowired
    QueriesOptimizedUseCase queriesOptimizedUseCase;

    /*** CONSULTAS CONTRA EL DOMINIO DE QUERIES ***/

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public CustomerDocumentDTO get(@RequestParam String customerId) {
        CustomerDocumentDTO customerDocumentDTO = this.queriesOptimizedUseCase.ejecutar(customerId);
        return customerDocumentDTO;
    }

    @GetMapping(value = "getAll", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDocumentDTO> getAll() {
        List<CustomerDocumentDTO> lista = new ArrayList<>();
        this.queriesOptimizedUseCase.ejecutar().forEach((customerDocument -> {
            lista.add(CustomerDocumentDTO.convertToDTO(customerDocument, CustomerDocumentDTO.class));
        }));
        return lista;
    }



}
