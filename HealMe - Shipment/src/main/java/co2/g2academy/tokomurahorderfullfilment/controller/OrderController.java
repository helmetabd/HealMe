/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.tokomurahorderfullfilment.controller;

import co2.g2academy.tokomurahorderfullfilment.model.Order;
import co2.g2academy.tokomurahorderfullfilment.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author personal
 */
@RestController
@RequestMapping("/api")
public class OrderController {
    
    @Autowired
    private OrderRepository repository;
    
    @PutMapping("/deliver/{id}")
    public String deliverOrder(@PathVariable Integer id){
        Order order = repository.findById(id).get();
        order.setStatus("DELIVERED");
        repository.save(order);
        return "OK";
    }
}
