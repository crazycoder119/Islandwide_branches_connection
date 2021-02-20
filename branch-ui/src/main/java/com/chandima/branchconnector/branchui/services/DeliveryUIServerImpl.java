package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.branchui.config.AccessToken;
import java.time.LocalDate;
import java.util.Date;

import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class DeliveryUIServerImpl implements DeliveryUIServer {

    @LoadBalanced
    @Autowired
    RestTemplate restTemplate;

    private  OrderUIServerImpl orderUIServer;

    private static final Logger DELIVERYUISERVICELOGGER = LoggerFactory.getLogger(DeliveryUIServerImpl.class);

    @Autowired
    public void setDelivery(OrderUIServerImpl orderUIServer) {
        this.orderUIServer = orderUIServer;

    }

    @Override
    public Model loadDeliveries(Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Delivery> customHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Delivery[]> responseEntity = restTemplate.exchange("http://deliveryservice/services/getAllDeliverys", HttpMethod.GET, customHttpEntity, Delivery[].class);
            model.addAttribute("deliveryList", responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    @Override
    public Model addDelivery(Delivery delivery, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        Order order = orderUIServer.getOrderByID(delivery.getOrderID());
        try {
            if(order==null) {
                DELIVERYUISERVICELOGGER.error("there is no order with the orderid");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the logger orderid is wrong");
            }else {
                JSONObject deliveryJsonObject = new JSONObject();
                deliveryJsonObject.put("id", delivery.getId());
                deliveryJsonObject.put("orderID", delivery.getOrderID());
                deliveryJsonObject.put("pickUpLocation", delivery.getPickUpLocation());
                deliveryJsonObject.put("deliveryLocation", delivery.getDeliveryLocation());
                deliveryJsonObject.put("orderDate", order.getOrderDate().toString());
                // have to add Estimate date as LocalDate
                deliveryJsonObject.put("doneStatus", delivery.getDoneStatus());
                deliveryJsonObject.put("deliveryCost", delivery.getDeliveryCost());
                HttpEntity<String> request =
                        new HttpEntity<String>(deliveryJsonObject.toString(), httpHeaders);

                Delivery checkDelivery = restTemplate.postForObject("http://deliveryservice/services/addDelivery", request, Delivery.class);
                if (checkDelivery!=null) {
                    model.addAttribute("deliveryupdates", checkDelivery);
                }else {
                    throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the uiservicelogger");
                }

            }

        }catch (HttpStatusCodeException e){
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }

        return model;
    }

    public Delivery getDeiveryByID(int deliverID) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Delivery> customHttpEntity = new HttpEntity<>(httpHeaders);
        String url = "http://deliveryservice/services/getDeliveryByID/"+deliverID;
        ResponseEntity<Delivery> responseEntity = restTemplate.exchange(url, HttpMethod.GET, customHttpEntity, Delivery.class);
        Delivery delivery =  responseEntity.getBody();
        return delivery;
    }

    public Delivery getDeiveryByOrderID(int orderID) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Delivery> customHttpEntity = new HttpEntity<>(httpHeaders);
        String url = "http://deliveryservice/services/getDeliveryByOrderID/"+orderID;
        ResponseEntity<Delivery> responseEntity = restTemplate.exchange(url, HttpMethod.GET, customHttpEntity, Delivery.class);
        Delivery delivery =  responseEntity.getBody();
        return delivery;
    }
}
