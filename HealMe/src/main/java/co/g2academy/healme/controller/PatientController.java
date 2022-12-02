/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Patient;
import co.g2academy.healme.repository.PatientRepository;
import co.g2academy.healme.validator.RegexEmailValidator;
import co.g2academy.healme.validator.RegexpasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
}
