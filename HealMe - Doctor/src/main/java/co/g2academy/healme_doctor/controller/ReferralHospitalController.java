/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_doctor.controller;

import co.g2academy.healme_doctor.model.Diagnose;
import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.model.Hospital;
import co.g2academy.healme_doctor.model.ReferralHospital;
import co.g2academy.healme_doctor.repository.DiagnoseRepository;
import co.g2academy.healme_doctor.repository.DoctorRepository;
import co.g2academy.healme_doctor.repository.HospitalRepository;
import co.g2academy.healme_doctor.repository.PatientRepository;
import co.g2academy.healme_doctor.repository.ReferralHospitalRepository;
import co.g2academy.healme_doctor.view.AddToReferral;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private DoctorRepository doctorRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private ReferralHospitalRepository referralRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    
    @GetMapping("/hospital")
    public List<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    @GetMapping("/hospital/{id}")
    public Hospital getHospitalById(@PathVariable Integer id) {
        return hospitalRepository.findById(id).get();
    }
    
    @PostMapping("/referral")
    public ResponseEntity sendReferralHospital(@RequestBody AddToReferral atr, Principal principal, @PathVariable Integer id){
        Doctor loggedInDoctor = doctorRepository.findDoctorByUsername(principal.getName());
        Optional<Hospital> opt = hospitalRepository.findById(atr.getHospitalId());
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Hospital Not Found");
        }
        Hospital h = opt.get();
        Optional<Diagnose> diagnoseOpt = diagnoseRepository.findById(atr.getDiagnoseId());
        if (diagnoseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Diagnose Not Found");
        }
        Diagnose diagnose = diagnoseOpt.get();
        ReferralHospital referralHospital = referralRepository.getReferralByDiagnose(diagnose);
        referralHospital.setDiagnose(diagnose);
        referralHospital.setDoctor(loggedInDoctor);
        referralHospital.setHospital(h);
        referralHospital.setPatient(diagnose.getPatient());
        referralHospital.setReferralDate(new Date());
        referralRepository.save(referralHospital);
        return ResponseEntity.ok("Success");
    }
    
    @GetMapping("/referral")
    public ResponseEntity getReferralHospital(Principal principal) {
        Doctor doctorFromDb = doctorRepository.findDoctorByUsername(principal.getName());
        ReferralHospital referral = referralRepository.getReferralByDoctor(doctorFromDb);
        if(referral == null){
            ResponseEntity.badRequest().body("You dont have any referral hospital");
        }
        return ResponseEntity.ok(referral.getHospital());
    }
}
