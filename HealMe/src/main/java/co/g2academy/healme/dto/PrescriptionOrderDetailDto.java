/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.dto;

import co.g2academy.healme.model.PrescriptionOrderDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author personal
 */
public class PrescriptionOrderDetailDto {

    private Integer storeFrontOrderItemId;
    private Integer medicineId;
    private Integer prescriptionOrderId;
    private String medicineName;
    private String dossage;
    private Integer price;
    private Integer quantity;
    @JsonIgnore
    private PrescriptionOrderDto order;

    public PrescriptionOrderDetailDto(PrescriptionOrderDetail o) {
        storeFrontOrderItemId = o.getId();
        prescriptionOrderId = o.getPrescriptionOrder().getId();
        medicineId = o.getMedicine().getId();
        medicineName = o.getMedicine().getName();
        dossage = o.getDossage();
        price = o.getPrice();
        quantity = o.getQuantity();
    }

    public Integer getStoreFrontOrderItemId() {
        return storeFrontOrderItemId;
    }

    public void setStoreFrontOrderItemId(Integer storeFrontOrderItemId) {
        this.storeFrontOrderItemId = storeFrontOrderItemId;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getPrescriptionOrderId() {
        return prescriptionOrderId;
    }

    public void setPrescriptionOrderId(Integer prescriptionOrderId) {
        this.prescriptionOrderId = prescriptionOrderId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDossage() {
        return dossage;
    }

    public void setDossage(String dossage) {
        this.dossage = dossage;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PrescriptionOrderDto getOrder() {
        return order;
    }

    public void setOrder(PrescriptionOrderDto order) {
        this.order = order;
    }

    
}
