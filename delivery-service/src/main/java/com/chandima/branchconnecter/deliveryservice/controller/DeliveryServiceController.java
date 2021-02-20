package com.chandima.branchconnecter.deliveryservice.controller;

import com.chandima.branchconnecter.deliveryservice.service.DeliveryService;
import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "/services")
public class DeliveryServiceController {

    @Autowired
    DeliveryService deliveryService;

    private static final Logger DELIVERYSERVICELOGGER = LoggerFactory.getLogger(DeliveryServiceController.class);


    @RequestMapping(value = "/getAllDeliverys", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Delivery> getDeliverys(){
        DELIVERYSERVICELOGGER.info("Request came getDeliverys");
        return  deliveryService.getAllDeliverys();
    }

    @RequestMapping(value = "/getDeliveryByID/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Delivery getDeliveryByID(@PathVariable int id){
        DELIVERYSERVICELOGGER.info("Request came getDeliveryByID");
        Delivery checkDelivery = deliveryService.getDeliveryByID(id);
        if(checkDelivery.equals(null)){
            DELIVERYSERVICELOGGER.error("unable to getDeliveryByID");
            return null;
        }
        return checkDelivery;
    }

    @RequestMapping(value = "/getDeliveryByOrderID/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Delivery getDeliveryByOrderID(@PathVariable int id){
        DELIVERYSERVICELOGGER.info("Request came getDeliveryByID");
        Delivery checkDelivery = deliveryService.getDeliveryByOrderID(id);
        if(checkDelivery==null){
            DELIVERYSERVICELOGGER.error("unable to getDeliveryByOrderID");
            return null;
        }
        return checkDelivery;
    }


    @RequestMapping(value = "/addDelivery", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create_profile')")
    public Delivery addDelivery(@RequestBody Delivery delivery){
        DELIVERYSERVICELOGGER.info("Request came addDelivery");
        Delivery checkDelivery = deliveryService.addDelivery(delivery);
        if(checkDelivery.equals(null)){
            DELIVERYSERVICELOGGER.error("unable to addDelivery");
            return null;
        }
        return checkDelivery;
    }

    @RequestMapping(value = "/updateDelivery", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('update_profile')")
    public Delivery updateDeliveryByID(@RequestBody Delivery delivery){
        DELIVERYSERVICELOGGER.info("Request came updateDeliveryByID");
        if (delivery.getOrderID()>0){
            if(validateOrder(delivery.getOrderID())==null){
                DELIVERYSERVICELOGGER.error("orderid is not correct");
                return null;
            }
        }
        Delivery checkDelivery = deliveryService.updateDeliveryByID(delivery);
        if(checkDelivery.equals(null)){
            DELIVERYSERVICELOGGER.error("unable to updateDeliveryByID");
            return null;
        }
        return checkDelivery;
    }


    @RequestMapping(value = "/deleteDelivery/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('delete_profile')")
    public Delivery deleteDelivery(@PathVariable int id){
        DELIVERYSERVICELOGGER.info("Request came deleteDelivery");
        Delivery delivery = deliveryService.deleteDelivery(id);
        return delivery;
    }


    public Order validateOrder(int id){
        String baseURL = "http://orderservice/services/getOrderByID/"+id;
        RestTemplate restTemplate = new RestTemplate();
        Order order =  restTemplate.getForObject(baseURL, Order.class);
        if(order!=null){
            return order;
        }
        return null;
    }

}
