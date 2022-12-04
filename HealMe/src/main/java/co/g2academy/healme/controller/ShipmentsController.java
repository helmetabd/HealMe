/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.controller;

import co.g2academy.healme.model.Medicine;
import co.g2academy.healme.model.Patient;
import co.g2academy.healme.model.Prescription;
import co.g2academy.healme.model.PrescriptionDetail;
import co.g2academy.healme.model.Shipments;
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
        Prescription prescription = prescriptionRepository.getPrescriptionByPatient(loggedInPatient);
        Shipments shipments = shipmentRepository.getShipmentByPrescriptionAndStatus(prescription, "ACTIVE");
        if (shipments == null) {
            shipments = new Shipments();
            shipments.getPrescription().setPatient(loggedInPatient);
            shipments.setStatus("ACTIVE");
            List<PrescriptionDetail> items = new ArrayList<>();
            shipments.getPrescription().setItems(items);
        }
        PrescriptionDetail item = null;
        for (PrescriptionDetail pd : shipments.getPrescription().getItems()) {
            if (pd.getMedicine().getId().equals(atc.getMedicineId())) {
                item = pd;
                break;
            }
        }
        if (item == null) {
            item = new PrescriptionDetail();
            item.setPrescription(shipments.getPrescription());
            item.setMedicine(m);
            item.setQuantity(atc.getQuantity());
            item.setPrice(m.getPrice());
            item.setDossage(item.getDossage());
            shipments.getPrescription().getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + atc.getQuantity());
        }
        prescriptionRepository.save(shipments.getPrescription());
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/cart")
    public ResponseEntity getCart(Principal principal) {
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        Prescription prescription = prescriptionRepository.getPrescriptionByPatient(loggedInPatient);
        Shipments shipments = shipmentRepository.getShipmentByPrescriptionAndStatus(prescription, "ACTIVE");
        return ResponseEntity.ok(shipments);
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(Principal principal) throws JsonProcessingException {
        Patient loggedInPatient = patientRepository.findPatientByUsername(principal.getName());
        Prescription prescription = prescriptionRepository.getPrescriptionByPatient(loggedInPatient);
        Shipments shipments = shipmentRepository.getShipmentByPrescriptionAndStatus(prescription, "ACTIVE");
        if (shipments == null) {
            return ResponseEntity.badRequest().body("Shipment not Found");
        }
        service.order(shipments, prescription);
        return ResponseEntity.ok("OK");
    }
}
