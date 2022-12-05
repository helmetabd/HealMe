/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme.repository;

import co.g2academy.healme.model.MedicineCart;
import co.g2academy.healme.model.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface MedicineCartRepository extends JpaRepository<MedicineCart, Integer>{
    public List<MedicineCart> findCartByPatient(Patient patient);
    public MedicineCart getCartByPatient(Patient patient);
}
