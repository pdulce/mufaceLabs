package com.arch.mfc.application.service.mongo;

import com.arch.mfc.application.domain.query.MongoCustomerOrder;
import com.arch.mfc.application.repository.query.CustomerOrderQueryRepository;
import com.arch.mfc.application.repository.query.CustomerQueryRepository;
import com.arch.mfc.infra.outputadapter.nonrelational.GenericQueryMongoImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderMongoService extends GenericQueryMongoImpl<MongoCustomerOrder> {

    @Autowired
    CustomerOrderQueryRepository customerOrderQueryRepository;
    public CustomerOrderMongoService() {}
    public CustomerOrderMongoService(MongoRepository<MongoCustomerOrder, String> repository) {
        this.repository = customerOrderQueryRepository;
    }
    public List<MongoCustomerOrder> getAllOrders() {
        return repository.findAll();
    }

    public MongoCustomerOrder saveOrder(MongoCustomerOrder order) {
        return repository.save(order);
    }

    public void deleteOrder(String id) {
        repository.deleteById(id);
    }
}