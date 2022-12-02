/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.healmeorderfullfilment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author personal
 */
@Entity
@Table(name = "t_order")
public class PrescriptionOrder implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer storeFrontOrderId;
    @Column(nullable = false)
    private Integer diagnoseId;
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date prescriptionDate;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<PrescriptionOrderDetail> orderItems;
    @Column(length = 100, nullable = false)
    private String patientName;
    @Column(length = 100, nullable = false)
    private String doctorName;
    @Column(length = 255, nullable = false)
    private String patientAddress;
    @Column(nullable = false)
    private Integer totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreFrontOrderId() {
        return storeFrontOrderId;
    }

    public void setStoreFrontOrderId(Integer storeFrontOrderId) {
        this.storeFrontOrderId = storeFrontOrderId;
    }

    public Integer getDiagnoseId() {
        return diagnoseId;
    }

    public void setDiagnoseId(Integer diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public List<PrescriptionOrderDetail> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<PrescriptionOrderDetail> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
