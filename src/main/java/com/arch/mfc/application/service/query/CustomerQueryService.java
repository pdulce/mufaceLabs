package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.querydomain.CustomerDocument;
import com.arch.mfc.application.repository.queryrepository.CustomerDocumentRepository;
import com.arch.mfc.infra.inputadapter.QueryDocumentInputAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryService extends QueryDocumentInputAdapter<CustomerDocument> {

}
