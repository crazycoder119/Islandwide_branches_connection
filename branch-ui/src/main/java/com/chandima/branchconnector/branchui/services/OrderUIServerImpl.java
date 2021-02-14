package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.branchui.config.AccessToken;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import com.chandima.branchconnector.commons.model.productservice.Product;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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

    @Autowired
    public void setOrder(CustomerUIServerImpl customerUIServer, ProductUIServerImpl productUIServer) {
        this.customerUIServer = customerUIServer;
        this.productUIServer = productUIServer;
    }

    @Override
    public Model loadOrders(Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Order> customerHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Order[]> responseEntity = restTemplate.exchange("http://orderservice/services/getAllOrders", HttpMethod.GET, customerHttpEntity, Order[].class);
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
        order.setOrderCost(product.getPrice().multiply(BigInteger.valueOf(order.getQuantity())));
        if(customer !=null && product != null){
            JSONObject orderJsonObject = new JSONObject();
            orderJsonObject.put("id",order.getId());
            orderJsonObject.put("customerID", order.getCustomerID());
            orderJsonObject.put("productID", order.getProductID());
            orderJsonObject.put("quantity", order.getQuantity());
            orderJsonObject.put("orderCost", order.getOrderCost());
            HttpEntity<String> request =
                    new HttpEntity<String>(orderJsonObject.toString(), httpHeaders);
            try {
                Order check = restTemplate.postForObject("http://orderservice/services/addOrder", request, Order.class);
                model.addAttribute("order", check);
            } catch (HttpStatusCodeException e) {
                ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
                model.addAttribute("error", responseEntity);
            }
        }
        return model;
    }

    public Order getOrderByID(int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Order> customerHttpEntity = new HttpEntity<>(httpHeaders);
        String url = "http://orderservice/services/getOrderByID/"+id;
        ResponseEntity<Order> responseEntity = restTemplate.exchange(url, HttpMethod.GET, customerHttpEntity, Order.class);
        Order order =  responseEntity.getBody();
        return order;
    }

}
