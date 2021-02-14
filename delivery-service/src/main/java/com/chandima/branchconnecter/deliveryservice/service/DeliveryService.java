package com.chandima.branchconnecter.deliveryservice.service;

import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;

import java.util.List;

public interface DeliveryService {
    Delivery getDeliveryByID(int id);

    Delivery addDelivery(Delivery delivery);

    Delivery deleteDelivery(int id);

    Delivery updateDeliveryByID(Delivery delivery);

    Delivery getDeliveryByOrderID(int id);

    List<Delivery> getAllDeliverys();
}
