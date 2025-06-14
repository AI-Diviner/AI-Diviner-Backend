package com.letfate.aidiviner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {
    @Autowired
    private RedisProperties property;

    private LettuceConnectionFactory getFactory(int index) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(this.property.getHost());
        config.setPort(this.property.getPort());
        config.setPassword(this.property.getPassword());
        config.setDatabase(index);
        return new LettuceConnectionFactory(config);
    }

    private <T> RedisTemplate<String, T> getTemplate(LettuceConnectionFactory factory, Class<T> type) {
        RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(type));
        return template;
    }

    @Primary
    @Bean
    public LettuceConnectionFactory captchaRedisFactoryBean() {
        return this.getFactory(0);
    }

    @Bean
    public RedisTemplate<String, String> captchaRedisBean() {
        return this.getTemplate(this.captchaRedisFactoryBean(), String.class);
    }

    @Bean
    public LettuceConnectionFactory accessTokenRedisFactoryBean() {
        return this.getFactory(2);
    }

    @Bean
    public RedisTemplate<String, Integer> accessTokenRedisBean() {
        return this.getTemplate(this.accessTokenRedisFactoryBean(), Integer.class);
    }
}