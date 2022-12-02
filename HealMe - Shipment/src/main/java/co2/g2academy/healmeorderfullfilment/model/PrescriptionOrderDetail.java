/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.healmeorderfullfilment.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author personal
 */
@Entity
@Table(name = "t_order_item")
public class PrescriptionOrderDetail implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer storeFrontOrderItemId;
    @Column(nullable = false)
    private Integer medicineId;
    @ManyToOne
    @JoinColumn(name = "id_prescription_order", nullable = false)
    private PrescriptionOrder order;
    @Column(length = 100, nullable = false)
    private String medicineName;
    @Column(length = 100, nullable = false)
    private String dossage;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public PrescriptionOrder getOrder() {
        return order;
    }

    public void setOrder(PrescriptionOrder order) {
        this.order = order;
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

    
}
