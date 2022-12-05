/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.g2academy.healme.repository;

import co.g2academy.healme.model.Patient;
import co.g2academy.healme.model.ReferralHospital;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author personal
 */
public interface ReferralHospitalRepository extends JpaRepository<ReferralHospital, Integer>{
    public ReferralHospital getReferralByPatient(Patient patient);
}
