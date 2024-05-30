package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.querydomain.CustomerDocument;
import com.arch.mfc.infra.inputadapter.QueryInputConsumerAdapter;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryServiceConsumerAdapter extends QueryInputConsumerAdapter<CustomerDocument> {

}
