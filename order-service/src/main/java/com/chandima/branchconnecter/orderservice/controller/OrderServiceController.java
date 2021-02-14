package com.chandima.branchconnecter.orderservice.controller;

import com.chandima.branchconnecter.orderservice.service.OrderService;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import com.chandima.branchconnector.commons.model.productservice.Product;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/services")
public class OrderServiceController extends WebSecurityConfigurerAdapter {


    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/getAllOrders", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Order> getOrders(){
        return orderService.getOrders();
    }

    @RequestMapping(value = "/getOrderByID/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Order getOrderByID(@PathVariable int id){
        Order checkOrder = orderService.getOrderByID(id);
        if (checkOrder == null){
            return null;
        }
        return checkOrder;
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create_profile')")
    public Order addOrder(HttpServletRequest request, @RequestBody Order order){
        order.setOrderDate(LocalDate.now());
        Order checkOrder = orderService.addOrder(order);
        if (checkOrder == null){
            return null;
        }
        return checkOrder;
    }

//    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
//    @PreAuthorize("hasAuthority('update_profile')")
//    public  Order updateOrderByID(@RequestBody Order order){
//        if(order.getQuantity()>0){
//            Product product = validateProduct(order.getProductID());
//            order.setOrderCost(product.getPrice().multiply(BigInteger.valueOf(order.getQuantity())));
//        }
//        Order checkOrder = orderService.updateOrderByID(order);
//        if (checkOrder == null){
//            return null;
//        }
//        return checkOrder;
//    }

    @RequestMapping(value = "/deleteOrder/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('delete_profile')")
    public Order deleteOrderByID(@PathVariable int id){
        Order checkOrder = orderService.deleteOrderByID(id);
        if (checkOrder == null){
            return null;
        }
        return checkOrder;
    }

//    @PreAuthorize("hasAuthority('read_profile')")
//    public Customer validateCustomer(String token, int id){
//        System.out.println("validatecustomer");
//        String baseURL = "http://customerservice/services/getCustomer/"+id;
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization", token);
//        System.out.println(token);
//        HttpEntity<String> httpEntity = new HttpEntity <String> (httpHeaders);
//
//
//
//        if(customer!=null){
//            return customer;
//        }
//        return null;
//    }
//    @PreAuthorize("hasAuthority('read_profile')")
//    public Product validateProduct(int id){
//        String baseURL = "http://productservice/services/getProduct/"+id;
//        RestTemplate restTemplate = new RestTemplate();
//        Product product = restTemplate.getForObject(baseURL, Product.class);
//        if(product!=null){
//            return product;
//        }
//        return null;
//    }

}
