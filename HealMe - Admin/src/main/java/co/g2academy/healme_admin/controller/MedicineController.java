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
    public ResponseEntity getMedicineById(@PathVariable Integer id) {
        Optional<Medicine> opt =  medicineRepsitory.findById(id);
        if(opt.isEmpty()){
            return ResponseEntity.badRequest().body("Medicine Not Found");
        }
        return ResponseEntity.ok(opt.get());
    }

    @PostMapping("/save/medicine")
    public ResponseEntity save(@RequestBody Medicine medicine, Principal principal) {
        User loggedInUser = userRepository.findUserByUsername(principal.getName());
        medicine.setUser(loggedInUser);
        Medicine medicineFromDb = medicineRepsitory.findMedicineByName(medicine.getName());
        Medicine newMedicine = null;
        if (medicineFromDb == null) {
            newMedicine = medicineRepsitory.save(medicine);
        } else {
            return ResponseEntity.badRequest().body("Book already exist");
        }
        return ResponseEntity.ok().body(newMedicine);
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
                mFromDb.setStocks(medicine.getStocks());
                mFromDb.setImage(medicine.getImage());
                Medicine updatedMedicine = medicineRepsitory.save(mFromDb);
                return ResponseEntity.ok(updatedMedicine);
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
        return ResponseEntity.badRequest().body("Medicine not found");
    }
//    @DeleteMapping("/product")
//    public String delete() {
//        productRepsitory.deleteAll();
//        return "success";
//    }
}
