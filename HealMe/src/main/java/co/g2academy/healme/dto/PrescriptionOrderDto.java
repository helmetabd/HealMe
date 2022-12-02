/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.dto;

import co.g2academy.healme.model.PrescriptionOrder;
import co.g2academy.healme.model.PrescriptionOrderDetail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author personal
 */
public class PrescriptionOrderDto {

    private Integer storeFrontOrderId;
    private Integer diagnoseId;
    private Date prescriptionDate;
    private String patientName;
    private String doctorName;
    private String patientAddress;
    private List<PrescriptionOrderDetailDto> orderItems;
    private Integer totalPrice;

    public PrescriptionOrderDto(PrescriptionOrder o) {
        storeFrontOrderId = o.getId();
        diagnoseId = o.getDiagnose().getId();
        prescriptionDate = o.getPrescriptionDate();
        patientName = o.getPatient().getName();
        patientAddress = o.getPatient().getAddress();
        doctorName = o.getDoctor().getName();
        totalPrice = o.getTotalPrice();
        orderItems = new ArrayList<>();
        for (PrescriptionOrderDetail item : o.getOrderItems()) {
            orderItems.add(new PrescriptionOrderDetailDto(item));
        }
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

    public List<PrescriptionOrderDetailDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<PrescriptionOrderDetailDto> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
