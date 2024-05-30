package com.arch.mfc.infra.config;

import com.arch.mfc.infra.event.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.sql.Timestamp;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    //@Bean
    //public RedisConnectionFactory redisConnectionFactory() {
        //return new LettuceConnectionFactory();
    //}

    @Bean
    public RedisTemplate<String, Event<?>> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Event<?>> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Configure key serializer
        template.setKeySerializer(new StringRedisSerializer());

        // Configure value serializer using Jackson
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Timestamp.class, new DateDeserializers.TimestampDeserializer());
        objectMapper.registerModule(new JavaTimeModule());

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
