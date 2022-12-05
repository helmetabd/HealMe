/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme_doctor.repository;

import co.g2academy.healme_doctor.model.Diagnose;
import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.model.Patient;
import co.g2academy.healme_doctor.model.Prescription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>{
    public List<Prescription> findPrescriptionByDoctor(Doctor doctor);
    public Prescription getPrescriptionByPatient(Patient patient);
    public Prescription getPrescriptionByDoctor(Doctor doctor);
    public Prescription findPrescriptionByDiagnose(Diagnose diagnose);
}
