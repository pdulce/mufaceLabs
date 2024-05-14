package com.arch.mfc.application.service;


import java.util.Map;

import com.arch.mfc.application.domain.Customer;
import com.arch.mfc.application.domain.Order;
import com.arch.mfc.infra.inputadapter.MessageBrokerServiceAbstract;
import org.springframework.stereotype.Component;

@Component
public class MessageBrokerService extends MessageBrokerServiceAbstract {


    public Map<String,Class<?>> getClasses() {
        Map<String,Class<?>> classes = Map.of(
                "customer", Customer.class,
                "orders", Order.class
        );
        return classes;
    }



    
}
