package com.mfc.infra.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationDefinedProperties {

    @Value("${arch.eventbroker.active}")
    private boolean eventbrokerActive;

    @Value("${arch.application-id}")
    private String applicationId;

}
