package com.arch.mfc.application.domain.querydomain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document(collection = "customers")
public class CustomerDocument {

    @Id
    private String id;
    private String name;
    private String country;

    private List<CustomerOrderDocument> customerOrders;


}
