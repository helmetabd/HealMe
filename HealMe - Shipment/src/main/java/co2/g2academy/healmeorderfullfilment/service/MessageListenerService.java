/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.healmeorderfullfilment.service;

import co2.g2academy.healmeorderfullfilment.model.PrescriptionOrder;
import co2.g2academy.healmeorderfullfilment.model.PrescriptionOrderDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import co2.g2academy.healmeorderfullfilment.repository.PrescriptionOrderRepository;

/**
 *
 * @author personal
 */
@Service
public class MessageListenerService implements MessageListener{

    @Autowired @Qualifier("redisPubSubTemplate")
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PrescriptionOrderRepository repository;
    private ObjectMapper mapper = new JsonMapper();
    
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            String json = new String(message.getBody());
            System.out.println("Getting Json Message: " + json);
            PrescriptionOrder order = mapper.readValue(json, new TypeReference<PrescriptionOrder>(){});
            for (PrescriptionOrderDetail item : order.getOrderItems()) {
                item.setOrder(order);
            }
            repository.save(order);
        } catch(JsonProcessingException ex){
            ex.printStackTrace();
        }
    }
    
}
