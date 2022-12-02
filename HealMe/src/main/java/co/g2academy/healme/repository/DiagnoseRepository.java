/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme.repository;

import co.g2academy.healme.model.Diagnose;
import co.g2academy.healme.model.Doctor;
import co.g2academy.healme.model.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface DiagnoseRepository extends JpaRepository<Diagnose, Integer>{
    public Diagnose findDiagnoseByDoctor(Doctor doctor);
    public List<Diagnose> findDiagnoseByPatient(Patient patient);
    
}
