package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.branchui.config.AccessToken;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import com.chandima.branchconnector.commons.model.productservice.Product;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductUIServerImpl implements ProductUIServer {
    @LoadBalanced
    @Autowired
    RestTemplate restTemplate;


    @Override
    public Model loadProducts(Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Product> customerHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Product[]> responseEntity = restTemplate.exchange("http://productservice/services/getAllProducts", HttpMethod.GET, customerHttpEntity, Product[].class);
            model.addAttribute("productList", responseEntity.getBody());
            System.out.println(responseEntity.getBody().length+">>>>>>>>>>>>");
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    @Override
    public Model addProduct(Product product, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        JSONObject customerJsonObject = new JSONObject();
        customerJsonObject.put("id", product.getId());
        customerJsonObject.put("productName", product.getProductName());
        customerJsonObject.put("productDescription", product.getProductDescription());
        customerJsonObject.put("price", product.getPrice());
        HttpEntity<String> request =
                new HttpEntity<String>(customerJsonObject.toString(), httpHeaders);
        try {
            Product checkProduct = restTemplate.postForObject("http://productservice/services/addProduct", request, Product.class);
            model.addAttribute("product", checkProduct);
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    @Override
    public Model updateColomboStock(Product product, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        JSONObject customerJsonObject = new JSONObject();
        customerJsonObject.put("id", product.getId());
        customerJsonObject.put("colomboStockQuantity", product.getColomboStockQuantity());
        HttpEntity<String> request =
                new HttpEntity<String>(customerJsonObject.toString(), httpHeaders);
        try {
            Product checkProduct = restTemplate.postForObject("http://productservice/services/updateColomboStock", request, Product.class);
            model.addAttribute("product", checkProduct);
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    @Override
    public Model kandyStockQuantity(Product product, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        JSONObject customerJsonObject = new JSONObject();
        customerJsonObject.put("id", product.getId());
        customerJsonObject.put("kandyStockQuantity", product.getKandyStockQuantity());
        HttpEntity<String> request =
                new HttpEntity<String>(customerJsonObject.toString(), httpHeaders);
        try {
            Product checkProduct = restTemplate.postForObject("http://productservice/services/updateKandyStock", request, Product.class);
            model.addAttribute("product", checkProduct);
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    @Override
    public Model galleStockQuantity(Product product, Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("id", product.getId());
        productJsonObject.put("galleStockQuantity", product.getGalleStockQuantity());
        HttpEntity<String> request =
                new HttpEntity<String>(productJsonObject.toString(), httpHeaders);
        try {
            Product checkProduct = restTemplate.postForObject("http://productservice/services/updateGalleStock", request, Product.class);
            model.addAttribute("product", checkProduct);
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    public  Product getProductByID(int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Product> customerHttpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Product> responseEntity = restTemplate.exchange("http://productservice/services/getProduct/"+id, HttpMethod.GET, customerHttpEntity, Product.class);
        return responseEntity.getBody();
    }
}
