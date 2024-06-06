package com.mfc.infra.controller;


import com.mfc.infra.utils.ErrorConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public abstract class BaseRestController {

   /*@Autowired
    CustomerCommandAdapter customerCommandService;
    @Autowired
    CustomerQueryServiceConsumerAdapter customerQueryService;

    @Autowired
    EventStoreConsumerAdapter eventStoreConsumerAdapter;*/

    @Autowired
    protected MessageSource messageSource;

    public static final String EUSKERA = "euskera";
    public static final String GALLEGO = "gallego";
    public static final String CATALAN = "catalan";
    public static final String CASTELLANO = "castellano";
    public static final String ENGLISH = "english";
    public final Map<String, Locale> mapLocales = new HashMap<>();

    protected Locale getLocale(String idioma) {
        if (mapLocales.isEmpty()) {
            mapLocales.put(EUSKERA, new Locale("eu", "ES"));
            mapLocales.put(GALLEGO, new Locale("gl", "ES"));
            mapLocales.put(CATALAN, new Locale("ca", "ES"));
            mapLocales.put(CASTELLANO, new Locale("es"));
            mapLocales.put(ENGLISH, new Locale("en"));
        }
        Locale locale = mapLocales.get(idioma);
        return locale == null ? Locale.getDefault() : locale;
    }

    @GetMapping(value = "hola", produces=MediaType.APPLICATION_JSON_VALUE)
    public String saludar() {
        String message = messageSource.getMessage(ErrorConstantes.GREETING, null, getLocale(CASTELLANO));
        return message == null ? messageSource.getMessage(ErrorConstantes.ERROR_NOT_FOUND, null,
                getLocale(CASTELLANO)) : message;
    }

    /*@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer create(@RequestBody @NotNull Customer customer) {
        Locale locale = "es" != null ? new Locale("es") : Locale.getDefault();
        String message = messageSource.getMessage("greeting", null, locale);
        customer.setId(UUID.randomUUID().getMostSignificantBits());
        return customerCommandService.insert(customer);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer update(@RequestBody @NotNull Customer customer) {
        try{
            return customerCommandService.update(customer);
        } catch (Throwable exc) {
            return null;
        }
    }

    @DeleteMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public void deleteByname(@RequestParam String name) {
        if (name != null) {
            List<Customer> customers = this.customerCommandService.findAllByFieldvalue("name", name);
            customers.forEach((customer) -> {
                try {
                    customerCommandService.delete(customer);
                } catch (Throwable exc) {
                    return;
                }
            });
        } else {
            this.customerCommandService.deleteAll();
        }
    }

    @GetMapping(value = "getAllFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllFromCommandDB() {
        return customerCommandService.findAll();
    }

    @GetMapping(value = "getByNameFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllWithName(@RequestParam String name) {
        return customerCommandService.findAllByFieldvalue("name", name);
    }

    @GetMapping(value = "getByCountryFromCommandDB", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAllOfThisCountry(@RequestParam String country) {
        return customerCommandService.findAllByFieldvalue("country", country);
    }

    @GetMapping(value = "get", produces=MediaType.APPLICATION_JSON_VALUE)
    public Customer get(@RequestParam Long customerId) {
        try{
            return customerCommandService.findById(customerId);
        } catch (Throwable exc) {
            return null;
        }
    }

    /// CONSULTAS CONTRA EL DOMINIO DE EVENTOS

    @GetMapping(value = "getAllFromEventStoreRedis", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllFromEventStoreRedis() {
        return eventStoreConsumerAdapter.findAll(customerCommandService.getDocumentEntityClassname());
    }

    @GetMapping(value = "getAllEventsFromCustomerIdFromRedis", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllEventsFromIdFromRedis(@RequestParam String customerId) {
        return eventStoreConsumerAdapter.findById(customerCommandService.getDocumentEntityClassname(), customerId);
    }

    /// CONSULTAS CONTRA EL DOMINIO DE QUERIES

    @GetMapping(value = "getFromQueryStoreMongoById", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getFromQueryStoreMongoById(@RequestParam String customerId) {
        return customerQueryService.findById(customerId);
    }

    @GetMapping(value = "getAllFromQueryStoreMongo", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getAllFromQueryStoreMongo() {
        return customerQueryService.findAll();
    }
    */


}
