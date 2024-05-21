package com.arch.mfc.infra.config;

/*import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;*/

//@Configuration
public class CommandAxonConfiguration {

    /*@Value("${axon.eventstore.mongo.username}")
    private String mongoUsername;

    @Value("${axon.eventstoremongo.password}")
    private String mongoPassword;
    @Value("${mongodb.url}")
    private String mongoUrl;

    @Value("${mongodb.port}")
    private String mongoPort;

    @Value("${mongodb.dbname}")
    private String mongoDbName;

    @Value("${mongodb.events.collection.name}")
    private String eventsCollectionName;

    @Value("${mongodb.events.snapshot.collection.name}")
    private String snapshotCollectionName;

    @Bean
    public Serializer axonJsonSerializer() {
        return new JacksonSerializer.Builder().build();
    }

    @Bean
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.
                createCredential(mongoUsername, mongoDbName, mongoPassword.toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(
                        new ServerAddress(mongoUrl, Integer.parseInt(mongoPort)))))
                .credential(credential)
                .build();

        return MongoClients.create(settings);
    }

    @Bean(name = "axonMongoTemplate")
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongoClient(), mongoDbName)
                .build();
    }

    @Bean
    public MongoEventStorageEngine eventStorageEngine(MongoTemplate mongoTemplate, Serializer serializer) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(mongoTemplate)
                .eventSerializer(serializer)
                .build();
    }

    @Bean
    public TokenStore tokenStore(MongoTemplate mongoTemplate, Serializer serializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(mongoTemplate)
                .serializer(serializer)
                .build();
    }*/

}