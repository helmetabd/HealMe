/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Consultation;
import co.g2academy.healme.model.Diagnose;
import co.g2academy.healme.model.Doctor;
import co.g2academy.healme.model.Patient;
import co.g2academy.healme.repository.ConsultationRepository;
import co.g2academy.healme.repository.DiagnoseRepository;
import co.g2academy.healme.repository.DoctorRepository;
import co.g2academy.healme.repository.PatientRepository;
import java.security.Principal;
import java.util.List;
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
public class DiagnoseController {
    
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private ConsultationRepository consultationRepository;
    
    @GetMapping("/diagnose")
    public ResponseEntity getDiagnoses(Principal principal) {
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        List<Consultation> consultation = consultationRepository.findConsultationByPatient(loggedInPatient);
        if(consultation == null){
            return ResponseEntity.badRequest().body("You dont have any consultation yet");
        }
        List<Diagnose> diagnose = diagnoseRepository.findDiagnoseByPatient(loggedInPatient);
        return ResponseEntity.ok(diagnose);
    }
    @GetMapping("/diagnose/{name}")
    public ResponseEntity getDiagnoseByDoctor(@PathVariable String name, Principal principal) {
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        Doctor doctor = doctorRepository.findDoctorByUsername(principal.getName());
        List<Consultation> consultation = consultationRepository.findConsultationByPatient(loggedInPatient);
        if(consultation == null){
            return ResponseEntity.badRequest().body("You dont have any consultation yet");
        }
        Diagnose diagnose = diagnoseRepository.findDiagnoseByDoctor(doctor);
        return ResponseEntity.ok(diagnose);
    }
}
