/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme_doctor.controller;

import co.g2academy.healme_doctor.model.Diagnose;
import co.g2academy.healme_doctor.model.Doctor;
import co.g2academy.healme_doctor.model.Medicine;
import co.g2academy.healme_doctor.model.Prescription;
import co.g2academy.healme_doctor.model.PrescriptionDetail;
import co.g2academy.healme_doctor.repository.DiagnoseRepository;
import co.g2academy.healme_doctor.repository.DoctorRepository;
import co.g2academy.healme_doctor.repository.MedicineRepository;
import co.g2academy.healme_doctor.repository.PrescriptionRepository;
import co.g2academy.healme_doctor.view.AddToPrescription;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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
public class PrescriptionController {
    
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
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
    
    @PostMapping("/add-to-prescription")
    public ResponseEntity addToCart(@RequestBody AddToPrescription atp, Principal principal) {
        Doctor loggedInDoctor = doctorRepository.findDoctorByUsername(principal.getName());
        Optional<Medicine> opt = medicineRepository.findById(atp.getMedicineId());
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().body("Medicine Not Found");
        }
        Medicine m = opt.get();
        Optional<Diagnose> diagnoseOpt = diagnoseRepository.findById(atp.getDiagnoseId());
        if (diagnoseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Diagnose Not Found");
        }
        Diagnose diagnose = diagnoseOpt.get();
        Prescription prescription = prescriptionRepository.findPrescriptionByDiagnose(diagnose);
        
        if (prescription == null) {
            prescription = new Prescription();
            prescription.setDiagnose(diagnose);
            prescription.setDoctor(loggedInDoctor);
            prescription.setPatient(diagnose.getPatient());
            prescription.setPrescriptionDate(new Date());
            List<PrescriptionDetail> items = new ArrayList<>();
            prescription.setItems(items);
        }
        PrescriptionDetail item = null;
        for (PrescriptionDetail pd : prescription.getItems()) {
            if (pd.getMedicine().getId().equals(atp.getMedicineId())) {
                item = pd;
                break;
            }
        }
        if (item == null) {
            item = new PrescriptionDetail();
            item.setPrescription(prescription); 
            item.setMedicine(m);
            item.setQuantity(atp.getQuantity());
            item.setPrice(m.getPrice());
            item.setDossage(item.getDossage());
            prescription.getItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + atp.getQuantity());
        }
        prescriptionRepository.save(prescription);
        return ResponseEntity.ok("OK");
    }
    
    @GetMapping("/prescription")
    public ResponseEntity getPrescription(Principal principal){
        Doctor loggedInDoctor = doctorRepository.findDoctorByUsername(principal.getName());
        List<Diagnose> diagnose = diagnoseRepository.findDiagnoseByDoctor(loggedInDoctor);
        if(diagnose == null){
            return ResponseEntity.badRequest().body("You dont have any diagnose yet");
        }
        List<Prescription> prescriptions = prescriptionRepository.findPrescriptionByDoctor(loggedInDoctor);
        return ResponseEntity.ok(prescriptions);
    }
    
    @GetMapping("/prescription/{id}")
    public ResponseEntity getPrescriptionById(@PathVariable Integer id, Principal principal){
        Doctor loggedInDoctor = doctorRepository.findDoctorByUsername(principal.getName());
        List<Diagnose> diagnose = diagnoseRepository.findDiagnoseByDoctor(loggedInDoctor);
        if(diagnose == null){
            return ResponseEntity.badRequest().body("You dont have any consultation yet");
        }
        Optional<Prescription> prescriptionOpt = prescriptionRepository.findById(id);
        if (prescriptionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Prescription Not Found");
        }
        Prescription prescription = prescriptionOpt.get();
        return ResponseEntity.ok(prescription);
    }
}
