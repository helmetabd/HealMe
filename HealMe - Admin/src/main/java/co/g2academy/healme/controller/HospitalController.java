/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Hospital;
import co.g2academy.healme.model.User;
import co.g2academy.healme.repository.HospitalRepository;
import co.g2academy.healme.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class HospitalController {

    @Autowired
    private HospitalRepository hospitalRepsitory;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hospital")
    public List<Hospital> getHospitals() {
        return hospitalRepsitory.findAll();
    }

    @GetMapping("/hospital/{id}")
    public Hospital getHospitalById(@PathVariable Integer id) {
        return hospitalRepsitory.findById(id).get();
    }

    @PostMapping("/save/hospital")
    public String save(@RequestBody Hospital hospital, Principal principal) {
        User loggedInUser = userRepository.findUserByUsername(principal.getName());
        hospital.setUser(loggedInUser);
        hospitalRepsitory.save(hospital);
        return "success";
    }

    @PutMapping("/update/hospital/{id}")
    public ResponseEntity update(@RequestBody Hospital hospital, Principal principal) {
        Optional<Hospital> opt = hospitalRepsitory.findById(hospital.getId());
        if (!opt.isEmpty()) {
            Hospital hFromDb = opt.get();
            if (hFromDb.getUser().getUsername().equals(principal.getName())) {
                hFromDb.setName(hospital.getName());
                hFromDb.setAddress(hospital.getAddress());
                hospitalRepsitory.save(hFromDb);
                return ResponseEntity.ok("success");
            }
        }
        return ResponseEntity.badRequest().body("Hospital not found");
    }

    @DeleteMapping("/hospital/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id, Principal principal) {
        Optional<Hospital> opt = hospitalRepsitory.findById(id);
        if (!opt.isEmpty()) {
            Hospital hFromDb = opt.get();
            if (hFromDb.getUser().getUsername().equals(principal.getName())) {
                hospitalRepsitory.deleteById(id);
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
