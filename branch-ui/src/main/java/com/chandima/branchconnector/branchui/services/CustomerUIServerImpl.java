package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.branchui.config.AccessToken;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerUIServerImpl implements CustomerUIServer {
    @LoadBalanced
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Model loadCustomer(Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Customer> customerHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Customer[]> responseEntity = restTemplate.exchange("http://customerservice/services/getAllCustomers", HttpMethod.GET, customerHttpEntity, Customer[].class);
            model.addAttribute("customerList", responseEntity.getBody());
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    @Override
    public Model addCustomer(Customer customer, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        JSONObject customerJsonObject = new JSONObject();
        customerJsonObject.put("id", customer.getId());
        customerJsonObject.put("customerName", customer.getCustomerName());
        customerJsonObject.put("customerAddress", customer.getCustomerAddress());
        customerJsonObject.put("customerEmail", customer.getCustomerEmail());
        customerJsonObject.put("customerTelephone", customer.getCustomerTelephone());
        HttpEntity<String> request =
                new HttpEntity<String>(customerJsonObject.toString(), httpHeaders);
        try {
            Customer checkCustomer = restTemplate.postForObject("http://customerservice/services/addCustomer", request, Customer.class);
            System.out.println(checkCustomer);
            if (checkCustomer!=null) {
                model.addAttribute("customerupdates", checkCustomer);
            }else {
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the id possibly there already customer exists with the id go to view customers");
            }
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    public Customer getCustomerByID(int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Customer> customerHttpEntity = new HttpEntity<>(httpHeaders);
        String url = "http://customerservice/services/getCustomer/"+id;
        ResponseEntity<Customer> responseEntity = restTemplate.exchange(url, HttpMethod.GET, customerHttpEntity, Customer.class);
        Customer customer =  responseEntity.getBody();
        return customer;
    }
}
