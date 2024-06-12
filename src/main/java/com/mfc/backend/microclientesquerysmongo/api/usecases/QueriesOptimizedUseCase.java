package com.mfc.backend.microclientesquerysmongo.api.usecases;

import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.backend.microclientesquerysmongo.domain.service.CustomerQueryServiceConsumerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
