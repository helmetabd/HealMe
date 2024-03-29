/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Patient;
import co.g2academy.healme.model.Prescription;
import co.g2academy.healme.repository.DiagnoseRepository;
import co.g2academy.healme.repository.PatientRepository;
import co.g2academy.healme.repository.PrescriptionRepository;
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
public class PrescriptionController {
    
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    @GetMapping("/prescription")
    public ResponseEntity getPrescription(Principal principal){
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        List<Prescription> prescriptions = prescriptionRepository.findPrescriptionByPatient(loggedInPatient);
        return ResponseEntity.ok(prescriptions);
    }
    @GetMapping("/prescription/{id}")
    public ResponseEntity getPrescriptionById(@PathVariable Integer id, Principal principal){
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        Optional<Prescription> prescriptionOpt = prescriptionRepository.findById(id);
        if(prescriptionOpt.isPresent() && prescriptionOpt.get().getPatient().getId() == loggedInPatient.getId()){
            return ResponseEntity.ok(prescriptionOpt.get());
        }
        return ResponseEntity.badRequest().body("Prescription not Found");
    }
}
