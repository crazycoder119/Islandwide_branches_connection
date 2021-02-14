package com.chandima.branchconnecter.deliveryservice.service;

import com.chandima.branchconnecter.deliveryservice.repository.DeliveryServiceRepository;
import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryServiceRepository deliveryServiceRepository;

    @Override
    public Delivery getDeliveryByID(int id) {
        Optional<Delivery> delivery = deliveryServiceRepository.findById(id);
        if (delivery.isPresent()){
            return delivery.get();
        }
        return null;
    }

    @Override
    public Delivery addDelivery(Delivery delivery) {
        if(getDeliveryByID(delivery.getId())==null){
            return deliveryServiceRepository.save(delivery);
        }
        return null;
    }

    @Override
    public Delivery deleteDelivery(int id) {
        Delivery delivery = getDeliveryByID(id);
        if(delivery!=null){
            deliveryServiceRepository.deleteById(id);
            return delivery;
        }
        return null;
    }

    @Override
    public Delivery updateDeliveryByID(Delivery delivery) {
        Delivery existDelivery = getDeliveryByID(delivery.getId());
        if(!existDelivery.equals(null)){
            if(delivery.getPickUpLocation()!=null){
                existDelivery.setPickUpLocation(delivery.getPickUpLocation());
            }
            if(delivery.getDeliveryLocation()!=null){
                existDelivery.setDeliveryLocation(delivery.getDeliveryLocation());
            }
            if(delivery.getEstimateDate()!=null){
                existDelivery.setEstimateDate(delivery.getEstimateDate());
            }
            if(delivery.getDoneStatus()!=null){
                existDelivery.setDoneStatus(delivery.getDoneStatus());
            }
            return existDelivery;
        }
        return null;
    }

    @Override
    public Delivery getDeliveryByOrderID(int id) {
        Optional<Delivery> checkDelivery = deliveryServiceRepository.getDeliveryByOrderID(id);
        if (checkDelivery.isPresent()){
            return checkDelivery.get();
        }
        return null;
    }

    @Override
    public List<Delivery> getAllDeliverys() {
        return deliveryServiceRepository.findAll();
    }
}
