/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.healmeorderfullfilment.repository;

import co2.g2academy.healmeorderfullfilment.model.PrescriptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface PrescriptionOrderRepository extends JpaRepository<PrescriptionOrder, Integer>{
    
}
