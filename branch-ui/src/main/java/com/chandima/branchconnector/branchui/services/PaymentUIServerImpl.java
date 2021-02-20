package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.branchui.config.AccessToken;
import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import com.chandima.branchconnector.commons.model.paymentservice.Payment;
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

import java.math.BigInteger;

@Service
public class PaymentUIServerImpl implements PaymentUIServer {
    @LoadBalanced
    @Autowired
    RestTemplate restTemplate;

    private OrderUIServerImpl orderUIServer;

    private  DeliveryUIServerImpl deliveryUIServer;

    private static final Logger PAYMENTUISERVICELOGGER = LoggerFactory.getLogger(PaymentUIServerImpl.class);

    @Autowired
    public void setDelivery(OrderUIServerImpl orderUIServer, DeliveryUIServerImpl deliveryUIServer) {
        this.orderUIServer = orderUIServer;
        this.deliveryUIServer = deliveryUIServer;
    }


    @Override
    public Model getPayment(Payment payment, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Payment> customHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            Order order = orderUIServer.getOrderByID(payment.getOrderID());
            if (order==null){
                PAYMENTUISERVICELOGGER.error("There is no order with orderid");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the uiservicelogger");
            }
            String URL = "http://paymentservice/services/getPayment/"+payment.getOrderID();

            ResponseEntity<Payment> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, customHttpEntity, Payment.class);
            model.addAttribute("paymentcheck", responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    @Override
    public Model addPayment(Payment payment, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        Order order = orderUIServer.getOrderByID(payment.getOrderID());
        try {
            if(order==null) {
                PAYMENTUISERVICELOGGER.error("There is no order with orderid");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the uiservicelogger");
            }
            else {
                Delivery delivery = deliveryUIServer.getDeiveryByOrderID(payment.getOrderID());
                payment.setOrderCost(order.getOrderCost());
                if (delivery == null) {
                    payment.setDeliveryCost(BigInteger.valueOf(0));
                } else {
                    payment.setDeliveryCost(delivery.getDeliveryCost());
                }
                if (payment.getPayedAmount() == null) {
                    payment.setPayedAmount(BigInteger.valueOf(0));
                }
                payment.setTotalCost(payment.getOrderCost().add(payment.getDeliveryCost()));

                JSONObject paymentJsonObject = new JSONObject();
                paymentJsonObject.put("orderID",payment.getOrderID());
                paymentJsonObject.put("deliverID", payment.getDeliverID());
                paymentJsonObject.put("orderCost", payment.getOrderCost());
                paymentJsonObject.put("deliveryCost", payment.getDeliveryCost());
                paymentJsonObject.put("totalCost", payment.getTotalCost());
                paymentJsonObject.put("payedAmount", payment.getPayedAmount());
                paymentJsonObject.put("balance", payment.getBalance());
                HttpEntity<String> request =
                        new HttpEntity<String>(paymentJsonObject.toString(), httpHeaders);

                Payment check = restTemplate.postForObject("http://paymentservice/services/updatePayment", request, Payment.class);
                model.addAttribute("paymentupdates", check);
            }

        }catch (HttpStatusCodeException e){
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }


        return model;
    }

    public void addInitialPayment(Order order) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        JSONObject paymentJsonObject = new JSONObject();
        paymentJsonObject.put("orderID", order.getId());
        paymentJsonObject.put("orderCost", order.getOrderCost());
        paymentJsonObject.put("payedAmount", BigInteger.valueOf(0));
        HttpEntity<String> request =
                new HttpEntity<String>(paymentJsonObject.toString(), httpHeaders);
        try {
            Payment checkPayment = restTemplate.postForObject("http://paymentservice/services/addInitialPayment", request, Payment.class);
            System.out.println(checkPayment);
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
        }
    }
}
