package com.chandima.branchconnecter.paymentservice.controller;

import com.chandima.branchconnecter.paymentservice.service.PaymentService;
import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import com.chandima.branchconnector.commons.model.paymentservice.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/services")
public class PaymentServiceController {

    @Autowired
    PaymentService paymentService;

    private static final Logger PAYMENTSERVICELOGGER = LoggerFactory.getLogger(PaymentServiceController.class);

    @RequestMapping(value = "/addInitialPayment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create_profile')")
    public Payment addInitialPayment(@RequestBody Payment payment){
        PAYMENTSERVICELOGGER.info("Request came addInitialPayment");
        Payment checkPayment = paymentService.addInitialPayment(payment);
        if(checkPayment == null){
            PAYMENTSERVICELOGGER.error("unable to getCustomerByID");
            return null;
        }
        return checkPayment;
    }


    @RequestMapping(value = "/addPayment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create_profile')")
    public Payment addPayment(@RequestBody Payment payment){
        PAYMENTSERVICELOGGER.info("Request came addPayment");
        Payment updatePayment = validatePaymentInfo(payment);
        if(updatePayment==null){
            PAYMENTSERVICELOGGER.info("Payment validation failed");
            return null;
        }
        if(payment.getPayedAmount()==null){
            payment.setPayedAmount(BigInteger.valueOf(0));
        }
        payment.setTotalCost(payment.getOrderCost().add(payment.getDeliveryCost()));
        payment.setBalance(payment.getTotalCost().subtract(payment.getPayedAmount()));
        Payment checkPayment = paymentService.addPayment(payment);

        if(checkPayment == null){
            PAYMENTSERVICELOGGER.error("unable to addPayment");
            return null;
        }
        return checkPayment;
    }

    public Payment validatePaymentInfo(Payment payment){
        Order order = getOrder(payment.getOrderID());
        if(order==null){
            return null;
        }
        payment.setOrderCost(order.getOrderCost());

        Delivery delivery = getDelivery(order.getId());
        if (delivery==null){
            payment.setDeliveryCost(BigInteger.valueOf(0));
        }else {
            payment.setDeliveryCost(delivery.getDeliveryCost());
        }
        return payment;
    }

    public Delivery getDelivery(int id){
        String baseURL = "http://deliveryservice/services/getDeliveryByOrderID/"+id;
        RestTemplate restTemplate = new RestTemplate();
        Delivery delivery =  restTemplate.getForObject(baseURL, Delivery.class);
        if(delivery == null){
            return null;
        }
        return delivery;
    }

    public Order getOrder(int id){
        String baseURL = "http://orderservice/services/getOrderByID/"+id;
        RestTemplate restTemplate = new RestTemplate();
        Order order =  restTemplate.getForObject(baseURL, Order.class);
        if(order == null){
            return null;
        }
        return order;
    }

    @RequestMapping(value = "/getPayment/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Payment getPaymentByOrderID(@PathVariable int id){
        PAYMENTSERVICELOGGER.info("Request came getPaymentByOrderID");
        Payment checkPayment = paymentService.getPaymentByOrderID(id);
        if(checkPayment == null){
            PAYMENTSERVICELOGGER.error("unable to getPaymentByOrderID");
            return null;
        }
        return checkPayment;
    }

    @RequestMapping(value = "/updatePayment", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('update_profile')")
    public Payment updatePaymentByOrderID(@RequestBody Payment payment){
        PAYMENTSERVICELOGGER.info("Request came updatePaymentByOrderID");
        Payment checkPayment = paymentService.updatePaymentByOrderID(payment);
        if(checkPayment == null){
            PAYMENTSERVICELOGGER.error("unable to updatePaymentByOrderID");
            return null;
        }
        return checkPayment;
    }

    @RequestMapping(value = "/deletePayment/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('delete_profile')")
    public Payment deletePayment(@PathVariable int id){
        PAYMENTSERVICELOGGER.info("Request came deletePayment");
        Payment checkPayment = paymentService.deletePayment(id);
        if(checkPayment == null){
            PAYMENTSERVICELOGGER.error("unable to deletePayment");
            return null;
        }
        return checkPayment;
    }

}
