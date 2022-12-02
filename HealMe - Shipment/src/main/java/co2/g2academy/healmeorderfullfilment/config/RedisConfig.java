/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.healmeorderfullfilment.config;

import co2.g2academy.healmeorderfullfilment.service.MessageListenerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
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
    
    @Bean
    public MessageListenerAdapter messageListener(MessageListenerService service){
        return new MessageListenerAdapter(service);
    }
    
    @Bean
    public RedisMessageListenerContainer messageContainer(MessageListenerAdapter adapter){
        ChannelTopic topic = new ChannelTopic("orderMessagePubSub");
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConfiConnectionFactory());
        container.addMessageListener(adapter, topic);
        return container;
    }
}
