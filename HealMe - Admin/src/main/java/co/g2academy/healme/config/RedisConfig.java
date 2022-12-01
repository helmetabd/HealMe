/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

/**
 *
 * @author personal
 */
@Configuration
public class RedisConfig {
    
    @Bean
    public JedisConnectionFactory jedisConfiConnectionFactory(){
        return new JedisConnectionFactory();
    }
    
    @Bean(name = "redisPubSubTemplate")
    public RedisTemplate<String, String> redisTemplate(){
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConfiConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(String.class));
        return template;
    }
        
}
