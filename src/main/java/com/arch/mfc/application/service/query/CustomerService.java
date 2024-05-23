package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.document.CustomerDocument;
import com.arch.mfc.infra.outputadapter.nonrelational.RepositoryRedisImpl;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends RepositoryRedisImpl<CustomerDocument> {


}