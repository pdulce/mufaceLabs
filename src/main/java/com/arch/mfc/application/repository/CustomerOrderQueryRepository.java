package com.arch.mfc.application.repository;

import com.arch.mfc.application.domain.query.MongoCustomerOrder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerOrderQueryRepository extends MongoRepository<MongoCustomerOrder, String> {

    /*
    @Override
    public void save(Map<String, Object> reg, Class<?> clazz) {
        String json = ConversionUtils.map2Jsonstring(reg);
        if (clazz.equals(CustomerOrder.class)) {
            CustomerOrder order = ConversionUtils.jsonStringToObject(json, CustomerOrder.class);
            customerOrderRepository.save(order);
            addOrderToCustomer(reg, true);
        } else if (clazz.equals(Customer.class)) {
            Customer customer = ConversionUtils.jsonStringToObject(json, Customer.class);
            customerRepository.save(customer);
        }
    }


    @Override
    public void delete(String id, Class<?> clazz) {
        if (clazz.equals(CustomerOrder.class)) {
            addOrderToCustomer(id, false);
            customerOrderRepository.deleteById(id);
        } else if (clazz.equals(Customer.class)) {
            customerRepository.deleteById(id);
        }
    } */

}