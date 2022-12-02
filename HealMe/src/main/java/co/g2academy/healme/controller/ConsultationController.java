/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Consultation;
import co.g2academy.healme.model.Doctor;
import co.g2academy.healme.model.Patient;
import co.g2academy.healme.repository.ConsultationRepository;
import co.g2academy.healme.repository.DoctorRepository;
import co.g2academy.healme.repository.PatientRepository;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class ConsultationController {
    
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ConsultationRepository consultationRepository;
    
    
    @GetMapping("/doctor")
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }
    
    @GetMapping("/doctor/{specialist}")
    public List<Doctor> getDoctorBySpecialist(@PathVariable String specialist) {
        return doctorRepository.findBySpecialist(specialist);
    }
    
    @PostMapping("/consult/{id}")
    public ResponseEntity consult(@RequestBody Consultation consultation, Principal principal, @PathVariable Integer id){
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);
        if (doctorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Doctor Not Found");
        }
        Doctor doctor = doctorOpt.get();
        consultation.setPatient(loggedInPatient);
        consultation.setDoctor(doctor);
        consultation.setConsultationDate(new Date());
        consultation.setSubject(consultation.getSubject());
        consultation.setStatus(consultation.getStatus());
        consultation.setDescription(consultation.getDescription());
        consultationRepository.save(consultation);
        return ResponseEntity.ok("Sending Consultation to " + doctor.getName() + " Success");
    }
    
    @GetMapping("/consult")
    public ResponseEntity getConsult(Principal principal) {
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        List<Consultation> consultation = consultationRepository.findConsultationByPatient(loggedInPatient);
        return ResponseEntity.ok(consultation);
    }
}
