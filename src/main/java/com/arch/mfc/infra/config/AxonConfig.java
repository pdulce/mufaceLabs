package com.arch.mfc.infra.config;

import com.arch.mfc.infra.event.handler.SimpleAggregate;
import com.google.gson.JsonSerializer;
import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.MongoClients;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ProducerFactory;


import java.util.HashMap;
import java.util.Map;


@Configuration
public class AxonConfig {

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerTrackingEventProcessor("myKafkaEventProcessor");
    }

    @Bean
    public CommandGateway commandGateway() {
        return DefaultCommandGateway.builder().commandBus(commandBus()).build();
    }

    @Bean
    public CommandBus commandBus() {
        return SimpleCommandBus.builder().build();
    }

    @Bean
    public AggregateFactory<SimpleAggregate> customerAggregateFactory() {
        return new GenericAggregateFactory<>(SimpleAggregate.class);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> producerConfig = new HashMap<>();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerConfig);
    }

    /*@Bean
    public EmbeddedEventStore eventStorageEngine() {
        return EmbeddedEventStore.builder().storageEngine(inMemoryEventStorageEngine()).build();
    }
    @Bean
    public InMemoryEventStorageEngine inMemoryEventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }*/

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(axonMongoTemplate())
                // Configuración opcional
                // .eventSerializer(eventSerializer()) // Personalizar el serializador de eventos si es necesario
                .build();
    }

    @Bean
    public MongoClientFactoryBean mongo() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setHost("192.168.56.1"); // Cambia esto según la configuración de tu MongoDB
        return mongo;
    }
    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(MongoClients.create("mongodb://192.168.56.1:27017").
                        getDatabase("db"))
                .build();
    }


}
