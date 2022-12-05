/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Patient;
import co.g2academy.healme.model.ReferralHospital;
import co.g2academy.healme.repository.DiagnoseRepository;
import co.g2academy.healme.repository.DoctorRepository;
import co.g2academy.healme.repository.PatientRepository;
import co.g2academy.healme.repository.ReferralHospitalRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author personal
 */
@RestController
@RequestMapping("/api")
public class ReferralHospitalController {
    
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private ReferralHospitalRepository referralRepository;
    
    @GetMapping("/referral")
    public ResponseEntity getReferralHospital(Principal principal) {
        Patient patientFromDb = patientRepository.findPatientByUsername(principal.getName());
        ReferralHospital referral = referralRepository.getReferralByPatient(patientFromDb);
        if(referral == null){
            ResponseEntity.badRequest().body("You dont have any referral hospital");
        }
        return ResponseEntity.ok(referral.getHospital());
    }
}
