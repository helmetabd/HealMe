/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_admin.controller;

import co.g2academy.healme_admin.model.Hospital;
import co.g2academy.healme_admin.model.User;
import co.g2academy.healme_admin.repository.HospitalRepository;
import co.g2academy.healme_admin.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@CrossOrigin(origins = "http://localhost:3000")
public class HospitalController {

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hospital")
    public List<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    @GetMapping("/hospital/{id}")
    public ResponseEntity getHospitalById(@PathVariable Integer id) {
        Optional<Hospital> opt =  hospitalRepository.findById(id);
        if(opt.isEmpty()){
            return ResponseEntity.badRequest().body("Student Not Found");
        }
        return ResponseEntity.ok(opt.get());
    }

    @PostMapping("/save/hospital")
    public ResponseEntity save(@RequestBody Hospital hospital, Principal principal) {
        User loggedInUser = userRepository.findUserByUsername(principal.getName());
        hospital.setUser(loggedInUser);
        Hospital hospitalFromDb = hospitalRepository.findHospitalByName(hospital.getName());
        Hospital newHospital = null;
        if (hospitalFromDb == null) {
            newHospital = hospitalRepository.save(hospital);
        } else {
            return ResponseEntity.badRequest().body("Hospital already exist");
        }
        return ResponseEntity.ok().body(newHospital);
    }

    @PutMapping("/update/hospital/{id}")
    public ResponseEntity update(@RequestBody Hospital hospital, Principal principal) {
        Optional<Hospital> opt = hospitalRepository.findById(hospital.getId());
        if (!opt.isEmpty()) {
            Hospital hFromDb = opt.get();
            if (hFromDb.getUser().getUsername().equals(principal.getName())) {
                hFromDb.setName(hospital.getName());
                hFromDb.setAddress(hospital.getAddress());
                hFromDb.setDescription(hospital.getDescription());
                hFromDb.setImage(hospital.getImage());
                Hospital updatedHospital = hospitalRepository.save(hFromDb);
                return ResponseEntity.ok(updatedHospital);
            }
        }
        return ResponseEntity.badRequest().body("Hospital not found");
    }

    @DeleteMapping("/hospital/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id, Principal principal) {
        Optional<Hospital> opt = hospitalRepository.findById(id);
        if (!opt.isEmpty()) {
            Hospital hFromDb = opt.get();
            if (hFromDb.getUser().getUsername().equals(principal.getName())) {
                hospitalRepository.deleteById(id);
                return ResponseEntity.ok("success");
            }
        }
        return ResponseEntity.badRequest().body("Product not found");
    }
//    @DeleteMapping("/product")
//    public String delete() {
//        productRepsitory.deleteAll();
//        return "success";
//    }
}
