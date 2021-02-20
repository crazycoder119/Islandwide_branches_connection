package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.branchui.config.AccessToken;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import com.chandima.branchconnector.commons.model.productservice.Product;
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
public class ProductUIServerImpl implements ProductUIServer {
    @LoadBalanced
    @Autowired
    RestTemplate restTemplate;
    private static final Logger PRODUCTUISERVICELOGGER = LoggerFactory.getLogger(ProductUIServerImpl.class);

    @Override
    public Model loadProducts(Model model) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Product> customHttpEntity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<Product[]> responseEntity = restTemplate.exchange("http://productservice/services/getAllProducts", HttpMethod.GET, customHttpEntity, Product[].class);
            model.addAttribute("productList", responseEntity.getBody());
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
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("id", product.getId());
        productJsonObject.put("productName", product.getProductName());
        productJsonObject.put("productDescription", product.getProductDescription());
        productJsonObject.put("price", product.getPrice());
        HttpEntity<String> request =
                new HttpEntity<String>(productJsonObject.toString(), httpHeaders);
        try {
            Product checkProduct = restTemplate.postForObject("http://productservice/services/addProduct", request, Product.class);
            if (checkProduct!=null) {
                model.addAttribute("productupdates", checkProduct);
            }else {
                PRODUCTUISERVICELOGGER.error("Product not saved probably product id is exist with another product");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the logger");
            }
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
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("id", product.getId());
        productJsonObject.put("colomboStockQuantity", product.getColomboStockQuantity());
        HttpEntity<String> request =
                new HttpEntity<String>(productJsonObject.toString(), httpHeaders);
        try {
            Product checkProduct = restTemplate.postForObject("http://productservice/services/updateColomboStock", request, Product.class);
            if (checkProduct!=null) {
                model.addAttribute("colombostockproduct", checkProduct);
            }else {
                PRODUCTUISERVICELOGGER.error("Product not saved probably product id not exist");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the uiservicelogger");
            }
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
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("id", product.getId());
        productJsonObject.put("kandyStockQuantity", product.getKandyStockQuantity());
        HttpEntity<String> request =
                new HttpEntity<String>(productJsonObject.toString(), httpHeaders);
        try {
            Product checkProduct = restTemplate.postForObject("http://productservice/services/updateKandyStock", request, Product.class);
            if (checkProduct!=null) {
                model.addAttribute("kandystockproduct", checkProduct);
            }else {
                PRODUCTUISERVICELOGGER.error("Product not saved probably product id not exist");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the uiservicelogger");
            }
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
            if (checkProduct!=null) {
                model.addAttribute("gallestockproduct", checkProduct);
            }else {
                PRODUCTUISERVICELOGGER.error("Product not saved probably product id not exist");
                throw new HttpServerErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "check the uiservicelogger");
            }
        } catch (HttpStatusCodeException e) {
            ResponseEntity responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
            model.addAttribute("error", responseEntity);
        }
        return model;
    }

    public  Product getProductByID(int id){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", AccessToken.getAccessToken());
        HttpEntity<Product> customHttpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Product> responseEntity = restTemplate.exchange("http://productservice/services/getProduct/"+id, HttpMethod.GET, customHttpEntity, Product.class);
        return responseEntity.getBody();
    }
}
