package com.arch.mfc.application.domain.query;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Data
@Document(collection = "orders")
public class MongoCustomerOrder {

    @Id
    private String id;
    private String customerId;
    private BigDecimal total;

}
