package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.querydomain.CustomerOrderDocument;
import com.arch.mfc.infra.inputadapter.QueryDocumentInputAdapter;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderQueryService extends QueryDocumentInputAdapter<CustomerOrderDocument> {

}
