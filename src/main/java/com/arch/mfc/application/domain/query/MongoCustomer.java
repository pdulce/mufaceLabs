package com.arch.mfc.application.domain.query;

import lombok.Data;
import org.springframework.data.annotation.Id;
        import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "customers")
public class MongoCustomer {

    @Id
    private String id;
    private String name;
    private String country;


}
