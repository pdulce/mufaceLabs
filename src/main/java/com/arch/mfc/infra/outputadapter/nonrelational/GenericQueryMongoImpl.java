package com.arch.mfc.infra.outputadapter.nonrelational;

import com.arch.mfc.infra.outputport.QueryRepoInterface;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import com.arch.mfc.infra.utils.ConversionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenericQueryMongoImpl<T> implements QueryRepoInterface<T> {

    protected MongoRepository<T, String> repository;

    public GenericQueryMongoImpl() {}

    public GenericQueryMongoImpl(MongoRepository<T, String> repository) {

        this.repository = repository;
    }

    @Override
    public void save(Map<String, Object> reg, Class<T> clazz) {
        String json = ConversionUtils.map2Jsonstring(reg);
        T order = ConversionUtils.jsonStringToObject(json, clazz);
        repository.save(order);
    }

    @Override
    public void delete(String id, Class<T> clazz) {
        repository.deleteById(id);
    }

    @Override
    public Map<String, Object> getById(String id, Class<T> clazz) {
        Optional<T> order = repository.findById(id);
        return order.map(ConversionUtils::objectToMap).orElse(null);
    }

    @Override
    public List<Map<String, Object>> getAll(Class<T> clazz) {
        List<T> orders = repository.findAll();
        return orders.stream().map(ConversionUtils::objectToMap).collect(Collectors.toList());
    }

    /*private void addOrderToCustomer(String orderId, boolean appendOrder) {
        Map<String, Object> order = getById(orderId, CustomerOrder.class);
        if (order != null) {
            addOrderToCustomer(order, appendOrder);
        }
    }

    private void addChildToParent(Map<String, Object> order, boolean appendOrder) {
        String customerId = (String) order.get("customerid");
        if (customerId == null) return;

        Optional<T> customerOpt = repository.findById(customerId);
        if (customerOpt.isPresent()) {
            T customer = customerOpt.get();
            List<CustomerOrder> orders = customer.getOrders();
            if (orders == null) {
                orders = new ArrayList<>();
            }
            orders = orders.stream()
                    .filter(el -> !el.getId().equals(order.get("id")))
                    .collect(Collectors.toList());

            if (appendOrder) {
                CustomerOrder newOrder = ConversionUtils.mapToObject(order, CustomerOrder.class);
                orders.add(newOrder);
            }

            customer.setOrders(orders);
            repository.save(customer);
        }
    }*/

}
