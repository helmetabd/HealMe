/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.dto;

import co.g2academy.healme.model.ShipmentsOrder;
import java.util.Date;

/**
 *
 * @author personal
 */
public class ShipmentsOrderDto {
    private Integer prescriptionOrderId;
    private Integer shipFrontOrderId;
    private Date shipmentsDate;
    private String shipmentType;
    private String status;
    
    public ShipmentsOrderDto(ShipmentsOrder o) {
        prescriptionOrderId = o.getPrescriptionOrder().getId();
        shipFrontOrderId = o.getId();
        shipmentsDate = o.getShipmentsDate();
        shipmentType = o.getShipmentType();
        status = o.getStatus();
    }

    public Integer getPrescriptionOrderId() {
        return prescriptionOrderId;
    }

    public void setPrescriptionOrderId(Integer prescriptionOrderId) {
        this.prescriptionOrderId = prescriptionOrderId;
    }

    public Integer getShipFrontOrderId() {
        return shipFrontOrderId;
    }

    public void setShipFrontOrderId(Integer shipFrontOrderId) {
        this.shipFrontOrderId = shipFrontOrderId;
    }

    public Date getShipmentsDate() {
        return shipmentsDate;
    }

    public void setShipmentsDate(Date shipmentsDate) {
        this.shipmentsDate = shipmentsDate;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
