/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Patient;
import co.g2academy.healme.repository.PatientRepository;
import co.g2academy.healme.validator.RegexEmailValidator;
import co.g2academy.healme.validator.RegexpasswordValidator;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author personal
 */
@RestController
@RequestMapping("/api")
public class PatientController {
    
    @Autowired
    private PatientRepository repository;
    @Autowired
    private RegexEmailValidator emailValidator;
    @Autowired
    private RegexpasswordValidator passwordValidator; 
    private PasswordEncoder paswordEncoder = new BCryptPasswordEncoder();
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Patient patient) {
        Patient patientFromDb = repository.findPatientByUsername(patient.getUsername());
        if (patientFromDb == null && emailValidator.emailPatternMatch(patient.getUsername())
                && passwordValidator.passwordPatternMatch(patient.getPassword())) {
            patient.setPassword(paswordEncoder.encode(patient.getPassword()));
            repository.save(patient);
        } else {
            return ResponseEntity.badRequest().body("email and password invalid");
        }
        return ResponseEntity.ok().body("OK");
    }
    
    @GetMapping("/patient")
    public ResponseEntity getPatient(Principal principal) {
        Patient patientFromDb = repository.findPatientByUsername(principal.getName());
        return ResponseEntity.ok(patientFromDb);
    }
    
    @PutMapping("/patient")
    public ResponseEntity update(@RequestBody Patient patient, Principal principal) {
        Patient patientFromDb = repository.findPatientByUsername(principal.getName());
        if(patientFromDb != null){
            patientFromDb.setName(patient.getName());
            patientFromDb.setAddress(patient.getAddress());
            patientFromDb.setBirthDate(patient.getBirthDate());
            repository.save(patientFromDb);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().body("Patient not found");
    }
}
