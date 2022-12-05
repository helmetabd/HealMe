/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_doctor.controller;

import co.g2academy.healme_doctor.model.Consultation;
import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.repository.ConsultationRepository;
import co.g2academy.healme_doctor.repository.DoctorRepository;
import co.g2academy.healme_doctor.repository.PatientRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author personal
 */
@RestController
@RequestMapping("/api")
public class ConsultationController {
    
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ConsultationRepository consultationRepository;
    
    @GetMapping("/consult/{id}")
    public ResponseEntity consult(Principal principal, @PathVariable Integer id){
        Doctor loggedInDoctor = doctorRepository.findDoctorByUsername(principal.getName());
        Optional<Consultation> consultationOpt = consultationRepository.findById(id);
        if (consultationOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Consultation Not Found");
        }
        Consultation consultation = consultationOpt.get();
        return ResponseEntity.ok(consultation);
    }
    
    @GetMapping("/consult")
    public ResponseEntity getConsult(Principal principal) {
        Doctor loggedInDoctor = doctorRepository.findDoctorByUsername(principal.getName());
        List<Consultation> consultation = consultationRepository.findConsultationByDoctor(loggedInDoctor);
        return ResponseEntity.ok(consultation);
    }
}
