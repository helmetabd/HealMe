/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.service;

import co.g2academy.tokomurah.dto.OrderDto;
import co.g2academy.tokomurah.model.Order;
import co.g2academy.tokomurah.model.OrderItem;
import co.g2academy.tokomurah.model.ShoppingCart;
import co.g2academy.tokomurah.model.ShoppingCartItem;
import co.g2academy.tokomurah.repository.OrderRepository;
import co.g2academy.tokomurah.repository.ShoppingCartRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author personal
 */
@Service
public class OrderService {

    @Autowired
    private ShoppingCartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    private ObjectMapper mapper = new JsonMapper();
    @Autowired
    private MessagePublisherService service;

    @Transactional(readOnly = false)
    public void checkout(ShoppingCart cart) throws JsonProcessingException {
        cart.setStatus("PROCESSED");
        cart.setTransactionDate(new Date());
        cartRepository.save(cart);
        Order order = new Order();
        order.setStatus("PROCESSED");
        order.setUser(cart.getUser());
        order.setTransactionDate(cart.getTransactionDate());
        Integer totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (ShoppingCartItem item : cart.getItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        OrderDto dto = new OrderDto(order);
        String json = mapper.writeValueAsString(dto);
        System.out.println("Sending Json to redis " + json);
        service.publish(json);
    }
}
