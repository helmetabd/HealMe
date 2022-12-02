/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co2.g2academy.healmeorderfullfilment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author personal
 */
@Entity
@Table(name = "t_shipments_order")
public class ShipmentsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "prescription_order_id", nullable = false)
    @JsonIgnore
    private PrescriptionOrder prescriptionOrder;
    
    @ManyToOne
    @JoinColumn(name = "shipments_order_id", nullable = false)
    @JsonIgnore
    private ShipmentsOrder shipmentsOrder;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date shipmentsDate;
    
    @Column(length = 10, nullable = false)
    private String status;
    
    @Column(length = 10, nullable = false)
    private String shipmentType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PrescriptionOrder getPrescriptionOrder() {
        return prescriptionOrder;
    }

    public void setPrescriptionOrder(PrescriptionOrder prescriptionOrder) {
        this.prescriptionOrder = prescriptionOrder;
    }

    public ShipmentsOrder getShipmentsOrder() {
        return shipmentsOrder;
    }

    public void setShipmentsOrder(ShipmentsOrder shipmentsOrder) {
        this.shipmentsOrder = shipmentsOrder;
    }

    public Date getShipmentsDate() {
        return shipmentsDate;
    }

    public void setShipmentsDate(Date shipmentsDate) {
        this.shipmentsDate = shipmentsDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }
    
    
}
