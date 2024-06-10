package com.mfc.backend.microclientes.api.eventdomain;

import com.mfc.backend.microclientes.domain.model.query.CustomerDocument;
import com.mfc.infra.controller.BaseRestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "events")
public class EventControllerAPI extends BaseRestController {


    /*** CONSULTAS CONTRA EL DOMINIO DE EVENTOS ***/

    @GetMapping(value = "getAllFromEventStoreCustomers", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllFromEventStoreCustomers() {
        return this.eventStoreConsumerAdapter.findAll(CustomerDocument.class.getSimpleName());
    }

    @GetMapping(value = "getTransactionsEvents", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getTransactionsEvents(@RequestParam String saga) {
        return this.eventStoreConsumerAdapter.findAll(saga);
    }

    @GetMapping(value = "getAllEventsFromCustomerId", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllEventsFromIdFromRedis(@RequestParam String customerId) {
        return this.eventStoreConsumerAdapter.findById(CustomerDocument.class.getSimpleName(), customerId);
    }

    @DeleteMapping(value = "deleteAlmacenCustomers", produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteAlmacenCustomers() {
        this.eventStoreConsumerAdapter.deleteAll(CustomerDocument.class.getSimpleName());
    }


}
