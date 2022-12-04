/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme.repository;

import co.g2academy.healme.model.Patient;
import co.g2academy.healme.model.Prescription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>{
    public List<Prescription> findPrescriptionByPatient(Patient patient);
    public Prescription getPrescriptionByPatient(Patient patient);
}
