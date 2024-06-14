package com.mfc.backend.microclientesquerysmongo.api.usecases;

import com.mfc.backend.microclientesquerysmongo.api.dto.CustomerDocumentDTO;
import com.mfc.backend.microclientesquerysmongo.domain.model.CustomerDocument;
import com.mfc.backend.microclientesquerysmongo.domain.service.CustomerServiceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueriesOptimizedUseCase {

    @Autowired
    CustomerServiceAdapter customerQueryServiceConsumerAdapter;
    public List<CustomerDocumentDTO> ejecutar() {
        return customerQueryServiceConsumerAdapter.buscarTodos();
    }

    public CustomerDocumentDTO ejecutar(String id) {
        return customerQueryServiceConsumerAdapter.buscarPorId(id);
    }


}
