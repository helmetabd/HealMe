/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_doctor.controller;

import co.g2academy.healme_doctor.model.Consultation;
import co.g2academy.healme_doctor.model.Diagnose;
import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.model.Patient;
import co.g2academy.healme_doctor.repository.ConsultationRepository;
import co.g2academy.healme_doctor.repository.DiagnoseRepository;
import co.g2academy.healme_doctor.repository.DoctorRepository;
import co.g2academy.healme_doctor.repository.PatientRepository;
import java.security.Principal;
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
public class DiagnoseController {
    
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private ConsultationRepository consultationRepository;
    
    @PostMapping("/diagnose")
    public ResponseEntity sendDiagnose(@RequestBody Diagnose diagnose, Principal principal) {
        Doctor loggedInDoctor = doctorRepository.findDoctorByUsername(principal.getName());
        Optional<Consultation> consultaionOpt = consultationRepository.findById(diagnose.getConsultation().getId());
        if (consultaionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Patient Not Found");
        }
        Consultation consultation = consultaionOpt.get();
        diagnose.setConsultation(consultation);
        diagnose.setPatient(consultation.getPatient());
        diagnose.setDoctor(loggedInDoctor);
        diagnose.setDescription(diagnose.getDescription());
        diagnoseRepository.save(diagnose);
        return ResponseEntity.ok("Success sending diagnose to " + consultation.getPatient().getName());
    }
}
