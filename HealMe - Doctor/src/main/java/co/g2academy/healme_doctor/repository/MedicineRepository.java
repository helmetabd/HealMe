/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme_doctor.repository;

import co.g2academy.healme_doctor.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface MedicineRepository extends JpaRepository<Medicine, Integer>{
}