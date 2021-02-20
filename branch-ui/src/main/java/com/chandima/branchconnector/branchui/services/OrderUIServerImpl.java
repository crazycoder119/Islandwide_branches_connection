package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.branchui.config.AccessToken;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import com.chandima.branchconnector.commons.model.productservice.Product;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

@Service
public class OrderUIServerImpl implements OrderUIServer {
    @LoadBalanced
    @Autowired
    RestTemplate restTemplate;

    private CustomerUIServerImpl customerUIServer;

    private ProductUIServerImpl productUIServer;

    private PaymentUIServerImpl paymentUIServer ;

    private static final Logger ORDERUISERVICELOGGER = LoggerFactory.getLogger(OrderUIServerImpl.class);

    @Autowired
    public void setOrder(CustomerUIServerImpl customerUIServer, ProductUIServerImpl productUIServer, PaymentUIServerImpl paymentUIServer) {
        this.customerUIServer = customerUIServer;
        this.productUIServer = productUIServer;
        this.paymentUIServer = paymentUIServer;
    }

    @Override
    public Model loadOrders(Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Order> customHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Order[]> responseEntity = restTemplate.exchange("http://orderservice/services/getAllOrders", HttpMethod.GET, customHttpEntity, Order[].class);
            model.addAttribute("orderList", responseEntity.getBody());
            System.out.println(responseEntity.getBody().length+">>>>>>>>>>>>");
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    @Override
    public Model addOrder(Order order, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        Customer customer = customerUIServer.getCustomerByID(order.getCustomerID());
        Product product = productUIServer.getProductByID(order.getProductID());

        try {
            if (customer==null){
                ORDERUISERVICELOGGER.error("Customer id is not exist");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the logger customerid is wrong");
            }
            else if(product ==null){
                ORDERUISERVICELOGGER.error("Product id is not exist");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the logger productid is wrong");
            }
            else{
                order.setOrderCost(product.getPrice().multiply(BigInteger.valueOf(order.getQuantity())));
                paymentUIServer.addInitialPayment(order);
                JSONObject orderJsonObject = new JSONObject();
                orderJsonObject.put("id",order.getId());
                orderJsonObject.put("customerID", order.getCustomerID());
                orderJsonObject.put("productID", order.getProductID());
                orderJsonObject.put("quantity", order.getQuantity());
                orderJsonObject.put("orderCost", order.getOrderCost());
                HttpEntity<String> request =
                        new HttpEntity<String>(orderJsonObject.toString(), httpHeaders);

                    Order check = restTemplate.postForObject("http://orderservice/services/addOrder", request, Order.class);
                    if (check!=null) {
                        model.addAttribute("updateorder", check);
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

    public Order getOrderByID(int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Order> customHttpEntity = new HttpEntity<>(httpHeaders);
        String url = "http://orderservice/services/getOrderByID/"+id;
        ResponseEntity<Order> responseEntity = restTemplate.exchange(url, HttpMethod.GET, customHttpEntity, Order.class);
        Order order =  responseEntity.getBody();
        return order;
    }

}
