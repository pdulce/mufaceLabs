package com.arch.mfc.application.service.mongo;

import com.arch.mfc.application.domain.query.MongoCustomerOrder;
import com.arch.mfc.infra.outputadapter.nonrelational.GenericQueryMongoImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderMongoService extends GenericQueryMongoImpl<MongoCustomerOrder> {

    public CustomerOrderMongoService() {}
    public CustomerOrderMongoService(Class<MongoCustomerOrder> entityClass) {
        super(entityClass);
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