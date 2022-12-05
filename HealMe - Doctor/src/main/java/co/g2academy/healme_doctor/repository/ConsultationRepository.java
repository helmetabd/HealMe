/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme_doctor.repository;

import co.g2academy.healme_doctor.model.Consultation;
import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.model.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface ConsultationRepository extends JpaRepository<Consultation, Integer>{
    public List<Consultation> findConsultationByDoctor(Doctor doctor);
    public Consultation findConsultationByPatient(Patient patient);
}
