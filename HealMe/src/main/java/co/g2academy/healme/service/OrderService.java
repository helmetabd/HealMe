/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.service;

import co.g2academy.healme.dto.PrescriptionOrderDto;
import co.g2academy.healme.dto.ShipmentsOrderDto;
import co.g2academy.healme.model.Prescription;
import co.g2academy.healme.model.PrescriptionDetail;
import co.g2academy.healme.model.PrescriptionOrder;
import co.g2academy.healme.model.PrescriptionOrderDetail;
import co.g2academy.healme.model.Shipments;
import co.g2academy.healme.model.ShipmentsOrder;
import co.g2academy.healme.repository.PrescriptionOrderRepository;
import co.g2academy.healme.repository.PrescriptionRepository;
import co.g2academy.healme.repository.ShipmentOrderRepository;
import co.g2academy.healme.repository.ShipmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author personal
 */
@Service
public class OrderService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private PrescriptionOrderRepository prescriptionOrderRepository;
    @Autowired
    private ShipmentOrderRepository shipmentOrderRepository;
    private ObjectMapper mapper = new JsonMapper();
    @Autowired
    private MessagePublisherService service;

    @Transactional(readOnly = false)
    public void order(Shipments shipments, Prescription prescription) throws JsonProcessingException {
        shipments.setStatus("PROCESSED");
        shipments.setShipmentsDate(new Date());
        shipments.setShipmentType(shipments.getShipmentType());
        shipmentRepository.save(shipments);
        prescription.setPrescriptionDate(new Date());
        prescriptionRepository.save(prescription);
        PrescriptionOrder po = new PrescriptionOrder();
        ShipmentsOrder so = new ShipmentsOrder();
        so.setStatus("PROCESSED");
        so.setShipmentsDate(shipments.getShipmentsDate());
        so.setShipmentType(shipments.getShipmentType());
        po.setDiagnose(prescription.getDiagnose());
        po.setPatient(prescription.getPatient());
        po.setDoctor(prescription.getDoctor());
        po.setPrescriptionDate(prescription.getPrescriptionDate());
        Integer totalPrice = 0;
        List<PrescriptionOrderDetail> orderItems = new ArrayList<>();
        for (PrescriptionDetail item : prescription.getItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
            PrescriptionOrderDetail orderItem = new PrescriptionOrderDetail();
            orderItem.setPrescriptionOrder(po);
            orderItem.setMedicine(item.getMedicine());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        }
        po.setOrderItems(orderItems);
        po.setTotalPrice(totalPrice);
        prescriptionOrderRepository.save(po);
        shipmentOrderRepository.save(so);
        PrescriptionOrderDto pdto = new PrescriptionOrderDto(po);
        ShipmentsOrderDto sdto = new ShipmentsOrderDto(so);
        String json = mapper.writeValueAsString(pdto) + "\n" + mapper.writeValueAsString(sdto);
        System.out.println("Sending Json to redis " + json);
        service.publish(json);
    }
}
