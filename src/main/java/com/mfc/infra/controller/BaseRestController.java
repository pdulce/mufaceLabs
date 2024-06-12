package com.mfc.infra.controller;

import com.mfc.infra.input.port.EventStoreInputPort;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConstantMessages;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public abstract class BaseRestController {

    Logger logger = LoggerFactory.getLogger(BaseRestController.class);

    @Autowired(required=false)
    protected EventStoreInputPort eventStoreConsumerAdapter;
    @Autowired(required=false)
    protected SagaOrchestratorPort orchestratorManager;

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

    public String saludar() {
        logger.info("orchestratorManager charged ? " +  (this.orchestratorManager != null));
        String message = messageSource.getMessage(ConstantMessages.GREETING, null, getLocale(CASTELLANO));
        logger.info("mapLocales charged ? " +  mapLocales.isEmpty());
        return message == null ? messageSource.getMessage(ConstantMessages.ERROR_NOT_FOUND, null,
                getLocale(CASTELLANO)) : message;
    }

    public String logout() {
        String message = messageSource.getMessage(ConstantMessages.LOGOUT, null, getLocale(CASTELLANO));
        return message == null ? messageSource.getMessage(ConstantMessages.ERROR_NOT_FOUND, null,
                getLocale(CASTELLANO)) : message;
    }


    /** ENDPOINTS QUE DAN ACCESO A LA INFORMACIÓN DE CUALQUIER AUDITORIA DE CUALQUIER APLICACION **/

    @GetMapping(value = "auditorias/{applicationId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllFromEventStoreCustomers(@PathVariable @NotEmpty String applicationId) {
        return this.eventStoreConsumerAdapter.findAllByApp(applicationId);
    }

    @GetMapping(value = "auditorias/{applicationId}/{almacen}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllFromEventStoreCustomers(@PathVariable @NotEmpty String applicationId,
                                                                   @PathVariable @NotEmpty String almacen) {
        // almacen: Customer.class.getSimpleName()
        return this.eventStoreConsumerAdapter.findAllByAppAndStore(applicationId, almacen);
    }

    @GetMapping(value = "auditorias/{applicationId}/{almacen}/{idAgregado}", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllEventsFromIdFromRedis(@PathVariable @NotEmpty String applicationId,
                                                    @PathVariable @NotEmpty String almacen,
                                                    @PathVariable @NotEmpty String idAgregado) {
        return this.eventStoreConsumerAdapter.findAllByAppAndStoreAndAggregatedId(applicationId, almacen, idAgregado);
    }

    /** ENDPOINTS QUE DAN ACCESO A LA INFORMACIÓN DE CUALQUIER TRANSACCION EN CUALQUIER APLICACION **/

    @GetMapping(value = "transacciones-distribuidas/{applicationId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllTransactionsOfApplication(@PathVariable @NotEmpty String applicationId,
                                                              @PathVariable @NotEmpty String saga) {
        return this.eventStoreConsumerAdapter.findAllByApp(applicationId);
    }

    @GetMapping(value = "transacciones-distribuidas/{applicationId}/{saga}", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllTransactionsOfSagaInApp(@PathVariable @NotEmpty String applicationId,
                                                              @PathVariable @NotEmpty String saga) {
        return this.eventStoreConsumerAdapter.findAllByAppAndStore(applicationId, saga);
    }

    @GetMapping(value = "transacciones-distribuidas/{applicationId}/{saga}/{transactionId}",
            produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllStepsInSagaTransactionId(@PathVariable @NotEmpty String applicationId,
                                                       @PathVariable @NotEmpty String saga,
                                                       @PathVariable @NotEmpty String transactionId) {
        return this.eventStoreConsumerAdapter.findAllByAppAndStoreAndAggregatedId(applicationId, saga, transactionId);
    }

    public String getSagaEstadoFinalizacion(String applicationId, String saga, String transaccionId) {
        logger.info("...Comprobamos si finalizó la transacción number: " + transaccionId + " de la saga " + saga);
        String[] messageKeyAndArgs = this.orchestratorManager.
                getLastStateOfTansactionInSaga(applicationId, saga, transaccionId);
        Object[] args = new Object[messageKeyAndArgs.length - 1];
        for (int i = 1; i < messageKeyAndArgs.length; i++) {
            args[i-1] = messageKeyAndArgs[i];
        }
        return messageSource.getMessage(messageKeyAndArgs[0], args, getLocale(CASTELLANO));
    }


}
