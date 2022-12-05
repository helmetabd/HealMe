/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme_doctor.repository;

import co.g2academy.healme_doctor.model.Diagnose;
import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.model.Patient;
import co.g2academy.healme_doctor.model.ReferralHospital;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface ReferralHospitalRepository extends JpaRepository<ReferralHospital, Integer>{
    public ReferralHospital getReferralByPatient(Patient patient);
    public ReferralHospital getReferralByDoctor(Doctor doctor);
    public ReferralHospital getReferralByDiagnose(Diagnose diagnose);
}
