/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.dto;

import co.g2academy.healme.model.Order;
import co.g2academy.healme.model.OrderDetail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author personal
 */
public class OrderDto {

    private Integer storeFrontOrderId;
    private Integer diagnoseId;
    private Date OrderDate;
    private String patientName;
    private String doctorName;
    private String patientAddress;
    private List<OrderDetailDto> orderItems;
    private Integer totalPrice;

    public OrderDto(Order o) {
        storeFrontOrderId = o.getId();
        diagnoseId = o.getDiagnose().getId();
        OrderDate = o.getOrderDate();
        patientName = o.getPatient().getName();
        patientAddress = o.getPatient().getAddress();
        doctorName = o.getDoctor().getName();
        totalPrice = o.getTotalPrice();
        orderItems = new ArrayList<>();
        for (OrderDetail item : o.getOrderItems()) {
            orderItems.add(new OrderDetailDto(item));
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

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
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

    public List<OrderDetailDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderDetailDto> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
