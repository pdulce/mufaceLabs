package com.arch.mfc.application.service.query;

import com.arch.mfc.application.domain.document.CustomerOrderDocument;
import com.arch.mfc.application.repository.document.CustomerOrderDocumentRepository;
import com.arch.mfc.infra.inputadapter.QueryDocumentInputAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerOrderQueryService extends QueryDocumentInputAdapter<CustomerOrderDocument> {

    @Autowired
    CustomerOrderDocumentRepository repository;

    public CustomerOrderQueryService(CustomerOrderDocumentRepository concreteRepository) {
        super(concreteRepository);
    }

    /*@Override
    public void save(Map<String, Object> reg, Class<CustomerOrderDocument> clazz) {
        super.save(reg, clazz);
        addOrderToCustomer(reg, true );
    }

    @Override
    public void delete(String id, Class<CustomerOrderDocument> clazz) {
        addOrderToCustomer( id, false );
        super.delete(id, clazz);
    }

    private void addOrderToCustomer( String orderId, boolean appendOrder ) {
        addOrderToCustomer(
                getById( orderId, CustomerOrderDocument.class),
                appendOrder
        );
    }

    private void addOrderToCustomer( Map<String, Object> order, boolean appendOrder ) {
        String customerId = (String) order.get("customerid");
        if ( customerId == null ) return;

        Map<String, Object> customer = ConversionUtils.jsonstring2Map(
                (String) redisTemplate.opsForHash().get(
                        getHashFromClass( CustomerOrderDocument.class ),
                        customerId
                )
        );

        List<Map<String, Object>> orders = (List<Map<String, Object>>) customer.get("orders");
        if ( orders == null ) orders = new ArrayList<>();

        orders = orders.stream()
                .filter( el -> !el.get("id").equals( order.get("id") ) )
                .collect( Collectors.toList() );

        if ( appendOrder ) orders.add( order );

        customer.put("orders", orders);

        redisTemplate.opsForHash().put(
                getHashFromClass( CustomerOrderDocument.class ),
                customerId,
                ConversionUtils.map2Jsonstring( customer )
        );
    }
    */

}