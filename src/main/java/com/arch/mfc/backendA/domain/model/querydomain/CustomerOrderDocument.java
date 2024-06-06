package com.arch.mfc.backendA.domain.model.querydomain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Data
@Document(collection = "orders")
public class CustomerOrderDocument {

    @Id
    private String id;
    private String  customer_id;
    private BigDecimal total;

}
