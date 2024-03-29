/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Medicine;
import co.g2academy.healme.model.MedicineCart;
import co.g2academy.healme.model.MedicineCartDetail;
import co.g2academy.healme.model.Patient;
import co.g2academy.healme.model.Prescription;
import co.g2academy.healme.model.Shipments;
import co.g2academy.healme.repository.MedicineCartRepository;
import co.g2academy.healme.repository.MedicineRepository;
import co.g2academy.healme.repository.PatientRepository;
import co.g2academy.healme.repository.PrescriptionRepository;
import co.g2academy.healme.repository.ShipmentRepository;
import co.g2academy.healme.service.OrderService;
import co.g2academy.healme.view.AddToCart;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.Principal;
import java.util.ArrayList;
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
public class ShipmentsController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private MedicineCartRepository cartRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private OrderService service;
    
    @PostMapping("/add-to-cart")
    public ResponseEntity addToCart(@RequestBody AddToCart atc, Principal principal) {
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        Optional<Medicine> opt = medicineRepository.findById(atc.getMedicineId());
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Medicine Not Found");
        }
        Medicine m = opt.get();
        MedicineCart medicineCart = cartRepository.getCartByPatient(loggedInPatient);
        Shipments shipments = shipmentRepository.getShipmentByMedicineCartAndStatus(medicineCart, "ACTIVE");
        if (shipments == null) {
            shipments = new Shipments();
            shipments.getMedicineCart().setPatient(loggedInPatient);
            shipments.setStatus("ACTIVE");
            List<MedicineCartDetail> items = new ArrayList<>();
            shipments.getMedicineCart().setItems(items);
        }
        MedicineCartDetail item = null;
        for (MedicineCartDetail mcd : shipments.getMedicineCart().getItems()) {
            if (mcd.getMedicine().getId().equals(atc.getMedicineId())) {
                item = mcd;
                break;
            }
        }
        if (item == null) {
            item = new MedicineCartDetail();
            item.setMedicineCart(shipments.getMedicineCart()); 
            item.setMedicine(m);
            item.setQuantity(atc.getQuantity());
            item.setPrice(m.getPrice());
            item.setDossage(item.getDossage());
            shipments.getMedicineCart().getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + atc.getQuantity());
        }
        cartRepository.save(shipments.getMedicineCart());
        shipmentRepository.save(shipments);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/medicine")
    public List<Medicine> getMedicines() {
        return medicineRepository.findAll();
    }

    @GetMapping("/medicine/{id}")
    public Medicine getMedicineById(@PathVariable Integer id) {
        Optional<Medicine> opt =  medicineRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        return null;
    }
    
    @GetMapping("/medicine/{name}")
    public Medicine getMedicineByName(@PathVariable String name) {
        Medicine medicine =  medicineRepository.findMedicineByName(name);
        if(medicine != null){
            return medicine;
        }
        return null;
    }
    
    @GetMapping("/medicine-cart")
    public ResponseEntity getMedicineCart(Principal principal) {
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        MedicineCart medicineCart = cartRepository.getCartByPatient(loggedInPatient);
        Shipments shipments = shipmentRepository.getShipmentByMedicineCartAndStatus(medicineCart, "ACTIVE");
        return ResponseEntity.ok(shipments);
    }
    
//    @GetMapping("/prescription-cart")
//    public ResponseEntity getPrescriptionCart(Principal principal) {
//        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
//        Prescription prescription = prescriptionRepository.getPrescriptionByPatient(loggedInPatient);
//        Shipments shipments = shipmentRepository.getShipmentByPrescriptionAndStatus(prescription, "ACTIVE");
//        return ResponseEntity.ok(shipments);
//    }

    @PostMapping("/checkout-cart")
    public ResponseEntity checkoutCart(Principal principal) throws JsonProcessingException {
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        MedicineCart medicineCart = cartRepository.getCartByPatient(loggedInPatient);
        Shipments shipments = shipmentRepository.getShipmentByMedicineCartAndStatus(medicineCart, "ACTIVE");
        if (shipments == null) {
            return ResponseEntity.badRequest().body("Shipment not Found");
        }
        service.orderCart(shipments, medicineCart);
        return ResponseEntity.ok("OK");
    }
    
//    @PostMapping("/checkout-prescription")
//    public ResponseEntity checkoutPrescription(Principal principal) throws JsonProcessingException {
//        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
//        Prescription prescription = prescriptionRepository.getPrescriptionByPatient(loggedInPatient);
//        Shipments shipments = shipmentRepository.getShipmentByPrescriptionAndStatus(prescription, "ACTIVE");
//        if (shipments == null) {
//            return ResponseEntity.badRequest().body("Shipment not Found");
//        }
//        service.orderPrescription(shipments, prescription);
//        return ResponseEntity.ok("OK");
//    }
}
