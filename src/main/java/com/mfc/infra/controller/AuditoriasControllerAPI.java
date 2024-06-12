package com.mfc.infra.controller;

import com.mfc.infra.input.port.EventStoreInputPort;
import com.mfc.infra.output.port.SagaOrchestratorPort;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping(value = "regalo")
public class AuditoriasControllerAPI {

    Logger logger = LoggerFactory.getLogger(AuditoriasControllerAPI.class);
    @Autowired
    protected MessageSource messageSource;

    @Autowired(required=false)
    protected SagaOrchestratorPort orchestratorManager;
    @Autowired(required=false)
    protected EventStoreInputPort eventStoreConsumerAdapter;

    /** ENDPOINTS QUE DAN ACCESO A LA INFORMACIÓN DE CUALQUIER AUDITORIA DE CUALQUIER APLICACION **/

    @GetMapping(value = "transacciones-simples/{applicationId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllFromEventStoreCustomers(@PathVariable @NotEmpty String applicationId) {
        //return this.eventStoreConsumerAdapter.findAllByApp(applicationId);
        throw new NotImplementedException("eventStoreConsumerAdapter.findAllByApp not implemented yet");
    }

    @GetMapping(value = "transacciones-simples/{applicationId}/{almacen}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllFromEventStoreCustomers(@PathVariable @NotEmpty String applicationId,
                                                                   @PathVariable @NotEmpty String almacen) {
        // almacen: Customer.class.getSimpleName()
        return this.eventStoreConsumerAdapter.findAllByAppAndStore(applicationId, almacen);
    }

    @GetMapping(value = "transacciones-simples/{applicationId}/{almacen}/{idAgregado}",
            produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllEventsFromIdFromRedis(@PathVariable @NotEmpty String applicationId,
                                                    @PathVariable @NotEmpty String almacen,
                                                    @PathVariable @NotEmpty String idAgregado) {
        return this.eventStoreConsumerAdapter.findAllByAppAndStoreAndAggregatedId(applicationId, almacen, idAgregado);
    }

    /** ENDPOINTS QUE DAN ACCESO A LA INFORMACIÓN DE CUALQUIER TRANSACCION EN CUALQUIER APLICACION **/

    @GetMapping(value = "transacciones-distribuidas/{applicationId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Object>> getAllTransactionsOfApplication(@PathVariable @NotEmpty String applicationId,
                                                                     @PathVariable @NotEmpty String saga) {
        //return this.eventStoreConsumerAdapter.findAllByApp(applicationId);
        throw new NotImplementedException("eventStoreConsumerAdapter.findAllByApp not implemented yet");
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


    @GetMapping(value = "transacciones-distribuidas/{applicationId}/{saga}/{transactionId}/status")
    private String getSagaEstadoFinalizacion(@PathVariable @NotEmpty String applicationId,
                                             @PathVariable @NotEmpty String saga,
                                             @PathVariable @NotEmpty String transactionId) {
        logger.info("...Comprobamos si finalizó la transacción number: " + transactionId + " de la saga " + saga);
        String[] messageKeyAndArgs = this.orchestratorManager.
                getLastStateOfTansactionInSaga(applicationId, saga, transactionId);
        Object[] args = new Object[messageKeyAndArgs.length - 1];
        for (int i = 1; i < messageKeyAndArgs.length; i++) {
            args[i-1] = messageKeyAndArgs[i];
        }
        return messageSource.getMessage(messageKeyAndArgs[0], args, new Locale("es"));
    }

}
