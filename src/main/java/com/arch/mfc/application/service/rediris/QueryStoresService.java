package com.arch.mfc.application.service.rediris;

import java.util.Map;

import com.arch.mfc.application.domain.command.Customer;
import com.arch.mfc.application.domain.command.CustomerOrder;
import com.arch.mfc.infra.inputadapter.QueryAbstractService;
import org.springframework.stereotype.Service;

@Service
public class QueryStoresService extends QueryAbstractService {


    public Map<String,Class<?>> getClasses() {
        Map<String,Class<?>> classes = Map.of(
                "Customer", Customer.class,
                "CustomerOrder", CustomerOrder.class
        );
        return classes;
    }



    
}
