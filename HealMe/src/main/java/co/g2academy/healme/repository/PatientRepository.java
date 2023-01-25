/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme.repository;

import co.g2academy.healme.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author personal
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{
    
    public Patient findPatientByUsername(String Username);
}
