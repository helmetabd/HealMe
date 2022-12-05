/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_doctor.controller;

import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.repository.DoctorRepository;
import co.g2academy.healme_doctor.validator.RegexEmailValidator;
import co.g2academy.healme_doctor.validator.RegexpasswordValidator;
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
public class DoctorController {
    
    @Autowired
    private DoctorRepository repository;
    @Autowired
    private RegexEmailValidator emailValidator;
    @Autowired
    private RegexpasswordValidator passwordValidator; 
    private PasswordEncoder paswordEncoder = new BCryptPasswordEncoder();
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Doctor doctor) {
        Doctor doctorFromDb = repository.findDoctorByUsername(doctor.getUsername());
        if (doctorFromDb == null && emailValidator.emailPatternMatch(doctor.getUsername())
                && passwordValidator.passwordPatternMatch(doctor.getPassword())) {
            doctor.setPassword(paswordEncoder.encode(doctor.getPassword()));
            repository.save(doctor);
        } else {
            return ResponseEntity.badRequest().body("email and password invalid");
        }
        return ResponseEntity.ok().body("OK");
    }
    
    @GetMapping("/doctor")
    public ResponseEntity getDoctor(Principal principal) {
        Doctor doctorFromDb = repository.findDoctorByUsername(principal.getName());
        return ResponseEntity.ok(doctorFromDb);
    }
    
    @PutMapping("/doctor")
    public ResponseEntity update(@RequestBody Doctor doctor, Principal principal) {
        Doctor doctorFromDb = repository.findDoctorByUsername(principal.getName());
        if(doctorFromDb != null){
            doctorFromDb.setName(doctor.getName());
            doctorFromDb.setSpecialist(doctor.getSpecialist());
            repository.save(doctorFromDb);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.badRequest().body("Doctor not found");
    }
}
