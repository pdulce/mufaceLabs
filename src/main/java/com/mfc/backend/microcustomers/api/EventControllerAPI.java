package com.mfc.backend.microcustomers.api;


import com.mfc.backend.microcustomers.domain.service.command.CustomerCommandAdapter;
import com.mfc.infra.controller.BaseRestController;
import com.mfc.infra.input.adapter.EventStoreConsumerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "events")
public class EventControllerAPI extends BaseRestController {

    @Autowired
    EventStoreConsumerAdapter eventStoreConsumerAdapter;

    @Autowired
    CustomerCommandAdapter customerCommandService;


    /*** CONSULTAS CONTRA EL DOMINIO DE EVENTOS ***/

    @GetMapping(value = "getAllFromEventStoreRedis", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllFromEventStoreRedis() {
        return this.eventStoreConsumerAdapter.findAll(customerCommandService.getDocumentEntityClassname());
    }

    @GetMapping(value = "getAllEventsFromCustomerIdFromRedis", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllEventsFromIdFromRedis(@RequestParam String customerId) {
        return this.eventStoreConsumerAdapter.findById(customerCommandService.getDocumentEntityClassname(), customerId);
    }

    @DeleteMapping(value = "deleteAlmacenCustomers", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAlmacenCustomers() {
        this.eventStoreConsumerAdapter.deleteAll(customerCommandService.getDocumentEntityClassname());
    }




}
