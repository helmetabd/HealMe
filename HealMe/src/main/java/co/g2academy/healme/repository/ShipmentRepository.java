/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme.repository;

import co.g2academy.healme.model.MedicineCart;
import co.g2academy.healme.model.Prescription;
import co.g2academy.healme.model.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface ShipmentRepository extends JpaRepository<Shipments, Integer>{
    public Shipments getShipmentByCartAndStatus(MedicineCart medicineCart, String status);
    public Shipments getShipmentByPrescriptionAndStatus(Prescription prescription, String status);
}
