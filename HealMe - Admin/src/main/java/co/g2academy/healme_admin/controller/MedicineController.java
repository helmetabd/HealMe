/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_admin.controller;

import co.g2academy.healme_admin.model.Medicine;
import co.g2academy.healme_admin.model.User;
import co.g2academy.healme_admin.repository.MedicineRepository;
import co.g2academy.healme_admin.repository.UserRepository;
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
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepsitory;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/medicine")
    public List<Medicine> getMedicines() {
        return medicineRepsitory.findAll();
    }

    @GetMapping("/medicine/{id}")
    public Medicine getMedicineById(@PathVariable Integer id) {
        Optional<Medicine> opt =  medicineRepsitory.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        return null;
    }

    @PostMapping("/save/medicine")
    public String save(@RequestBody Medicine medicine, Principal principal) {
        User loggedInUser = userRepository.findUserByUsername(principal.getName());
        medicine.setUser(loggedInUser);
        medicineRepsitory.save(medicine);
        return "success";
    }

    @PutMapping("/update/medicine/{id}")
    public ResponseEntity update(@RequestBody Medicine medicine, Principal principal) {
        Optional<Medicine> opt = medicineRepsitory.findById(medicine.getId());
        if (!opt.isEmpty()) {
            Medicine mFromDb = opt.get();
            if (mFromDb.getUser().getUsername().equals(principal.getName())) {
                mFromDb.setDescription(medicine.getDescription());
                mFromDb.setName(medicine.getName());
                mFromDb.setPrice(medicine.getPrice());
                mFromDb.setStock(medicine.getStock());
                medicineRepsitory.save(mFromDb);
                return ResponseEntity.ok("success");
            }
        }
        return ResponseEntity.badRequest().body("Medicine not found");
    }

    @DeleteMapping("/medicine/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id, Principal principal) {
        Optional<Medicine> opt = medicineRepsitory.findById(id);
        if (!opt.isEmpty()) {
            Medicine mFromDb = opt.get();
            if (mFromDb.getUser().getUsername().equals(principal.getName())) {
                medicineRepsitory.deleteById(id);
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
