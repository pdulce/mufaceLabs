package com.mfc.infra.controller;

import com.mfc.infra.output.port.SagaOrchestratorPort;
import com.mfc.infra.utils.ConstantMessages;
import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public abstract class BaseRestController {

    Logger logger = LoggerFactory.getLogger(BaseRestController.class);

    @Autowired
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

    public String getSagaEstadoFinalizacion(@RequestParam @NotEmpty String saga,
                                            @RequestParam @NotEmpty String transaccionId) {
        logger.info("vemos si la saga finalizó la saga " + saga + " en su transacción number: " + transaccionId);
        String messageKey = this.orchestratorManager.getLastStateOfTansactionInSaga(saga, transaccionId);
        Object[] args = null;
        if (messageKey.contentEquals(ConstantMessages.ERROR_NOT_FOUND)) {
            args = new Object[]{"núm. transaction: " + transaccionId + " in saga: " + saga};
        }
        return messageSource.getMessage(messageKey, args, getLocale(CASTELLANO));
    }

}
