/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.healmeorderfullfilment.controller;

import co2.g2academy.healmeorderfullfilment.model.PrescriptionOrder;
import co2.g2academy.healmeorderfullfilment.model.ShipmentsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co2.g2academy.healmeorderfullfilment.repository.PrescriptionOrderRepository;
import co2.g2academy.healmeorderfullfilment.repository.ShipmentsOrderRepository;

/**
 *
 * @author personal
 */
@RestController
@RequestMapping("/api")
public class OrderController {
    
    @Autowired
    private PrescriptionOrderRepository prepository;
    @Autowired
    private ShipmentsOrderRepository srepository;
    
    @PutMapping("/order/{id}")
    public String deliverOrder(@PathVariable Integer id){
        PrescriptionOrder po = prepository.findById(id).get();
        ShipmentsOrder so = srepository.findById(id).get();
        so.setStatus("DELIVERED");
        srepository.save(so);
        prepository.save(po);
        return "OK";
    }
}
