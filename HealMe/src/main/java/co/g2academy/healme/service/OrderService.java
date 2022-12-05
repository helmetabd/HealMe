/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.g2academy.healme.service;

import co.g2academy.healme.dto.OrderDto;
import co.g2academy.healme.dto.ShipmentsOrderDto;
import co.g2academy.healme.model.MedicineCart;
import co.g2academy.healme.model.MedicineCartDetail;
import co.g2academy.healme.model.Prescription;
import co.g2academy.healme.model.PrescriptionDetail;
import co.g2academy.healme.model.Order;
import co.g2academy.healme.model.OrderDetail;
import co.g2academy.healme.model.Shipments;
import co.g2academy.healme.model.ShipmentsOrder;
import co.g2academy.healme.repository.MedicineCartRepository;
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
import co.g2academy.healme.repository.OrderRepository;

/**
 *
 * @author personal
 */
@Service
public class OrderService {

    @Autowired
    private MedicineCartRepository medicineCartRepository;
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShipmentOrderRepository shipmentOrderRepository;
    private ObjectMapper mapper = new JsonMapper();
    @Autowired
    private MessagePublisherService service;

    @Transactional(readOnly = false)
    public void orderCart(Shipments shipments, MedicineCart medicineCart) throws JsonProcessingException {
        shipments.setStatus("PROCESSED");
        shipments.setShipmentsDate(new Date());
        shipments.setShipmentType(shipments.getShipmentType());
        shipmentRepository.save(shipments);
        medicineCart.setCartDate(new Date());
        medicineCartRepository.save(medicineCart);
        Order o = new Order();
        ShipmentsOrder so = new ShipmentsOrder();
        so.setStatus("PROCESSED");
        so.setShipmentsDate(shipments.getShipmentsDate());
        so.setShipmentType(shipments.getShipmentType());
        o.setDiagnose(medicineCart.getDiagnose());
        o.setPatient(medicineCart.getPatient());
        o.setDoctor(medicineCart.getDoctor());
        o.setOrderDate(medicineCart.getCartDate());
        Integer totalPrice = 0;
        List<OrderDetail> orderItems = new ArrayList<>();
        for (MedicineCartDetail item : medicineCart.getItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
            OrderDetail orderItem = new OrderDetail();
            orderItem.setOrder(o);
            orderItem.setMedicine(item.getMedicine());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        }
        o.setOrderItems(orderItems);
        o.setTotalPrice(totalPrice);
        orderRepository.save(o);
        shipmentOrderRepository.save(so);
        OrderDto odto = new OrderDto(o);
        ShipmentsOrderDto sdto = new ShipmentsOrderDto(so);
        String json = mapper.writeValueAsString(odto) + "\n" + mapper.writeValueAsString(sdto);
        System.out.println("Sending Json to redis " + json);
        service.publish(json);
    }
    
    @Transactional(readOnly = false)
    public void orderPrescription(Shipments shipments, Prescription prescription) throws JsonProcessingException {
        shipments.setStatus("PROCESSED");
        shipments.setShipmentsDate(new Date());
        shipments.setShipmentType(shipments.getShipmentType());
        shipmentRepository.save(shipments);
        prescription.setPrescriptionDate(new Date());
        prescriptionRepository.save(prescription);
        Order o = new Order();
        ShipmentsOrder so = new ShipmentsOrder();
        so.setStatus("PROCESSED");
        so.setShipmentsDate(shipments.getShipmentsDate());
        so.setShipmentType(shipments.getShipmentType());
        o.setDiagnose(prescription.getDiagnose());
        o.setPatient(prescription.getPatient());
        o.setDoctor(prescription.getDoctor());
        o.setOrderDate(prescription.getPrescriptionDate());
        Integer totalPrice = 0;
        List<OrderDetail> orderItems = new ArrayList<>();
        for (PrescriptionDetail item : prescription.getItems()) {
            totalPrice += item.getPrice() * item.getQuantity();
            OrderDetail orderItem = new OrderDetail();
            orderItem.setOrder(o);
            orderItem.setMedicine(item.getMedicine());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        }
        o.setOrderItems(orderItems);
        o.setTotalPrice(totalPrice);
        orderRepository.save(o);
        shipmentOrderRepository.save(so);
        OrderDto odto = new OrderDto(o);
        ShipmentsOrderDto sdto = new ShipmentsOrderDto(so);
        String json = mapper.writeValueAsString(odto) + "\n" + mapper.writeValueAsString(sdto);
        System.out.println("Sending Json to redis " + json);
        service.publish(json);
    }
}
