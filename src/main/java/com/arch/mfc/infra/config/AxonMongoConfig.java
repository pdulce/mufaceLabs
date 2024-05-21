package com.arch.mfc.infra.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.thoughtworks.xstream.XStream;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;

@Configuration
public class AxonMongoConfig {

    @Value("${spring.data.mongodb.username}")
    private String mongoUsername;

    @Value("${spring.data.mongodb.password}")
    private String mongoPassword;
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDbName;

    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        // Permitir las clases necesarias para la serialización
        xStream.allowTypesByWildcard(new String[] {
                "com.arch.mfc.**", // Cambia esto a tu paquete específico
                "org.axonframework.**"
        });
        return xStream;
    }
    //@Bean
    //@Primary
    public Serializer myserializer(XStream xStream) {
        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
    @Bean
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.
                createCredential(mongoUsername, mongoDbName, mongoPassword.toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Collections.
                        singletonList(new ServerAddress(mongoHost, Integer.parseInt(mongoPort)))))
                .credential(credential)
                .build();

        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongoClient(), mongoDbName)
                .build();
    }

    @Bean
    public MongoEventStorageEngine eventStorageEngine(MongoTemplate mongoTemplate, Serializer serializer) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(mongoTemplate)
                .eventSerializer(myserializer(xStream()))
                .build();
    }

    @Bean
    public TokenStore tokenStore(MongoTemplate mongoTemplate, Serializer serializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(mongoTemplate)
                .serializer(myserializer(xStream()))
                .build();
    }


    /*@Autowired
    public void configure(EventProcessingConfigurer configurer, Serializer serializer) {
        configurer.configureDefaultStreamableMessageSource(conf ->
                (StreamableMessageSource<TrackedEventMessage<?>>) serializer);
    }*/

}
