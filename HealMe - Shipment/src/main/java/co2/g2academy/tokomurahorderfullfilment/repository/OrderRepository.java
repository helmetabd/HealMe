/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.tokomurahorderfullfilment.repository;

import co2.g2academy.tokomurahorderfullfilment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface OrderRepository extends JpaRepository<Order, Integer>{
    
}
