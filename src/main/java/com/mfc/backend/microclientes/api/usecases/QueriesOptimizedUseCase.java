package com.mfc.backend.microclientes.api.usecases;

import com.mfc.backend.microclientes.domain.model.query.CustomerDocument;
import com.mfc.backend.microclientes.domain.service.query.CustomerQueryServiceConsumerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class QueriesOptimizedUseCase {

    @Autowired
    CustomerQueryServiceConsumerAdapter customerQueryServiceConsumerAdapter;
    public List<CustomerDocument> ejecutar() {
        return customerQueryServiceConsumerAdapter.findAll();
    }

    public CustomerDocument ejecutar(String id) {
        return customerQueryServiceConsumerAdapter.findById(id);
    }


}
