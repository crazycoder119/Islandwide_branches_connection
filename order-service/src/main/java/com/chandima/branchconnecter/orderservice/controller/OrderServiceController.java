package com.chandima.branchconnecter.orderservice.controller;

import com.chandima.branchconnecter.orderservice.service.OrderService;
import com.chandima.branchconnector.commons.model.orderservice.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger ORDERSERVICELOGGER = LoggerFactory.getLogger(OrderServiceController.class);

    @RequestMapping(value = "/getAllOrders", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Order> getOrders(){
        ORDERSERVICELOGGER.info("Request came getOrders");
        return orderService.getOrders();
    }

    @RequestMapping(value = "/getOrderByID/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Order getOrderByID(@PathVariable int id){
        ORDERSERVICELOGGER.info("Request came getOrders");
        Order checkOrder = orderService.getOrderByID(id);
        if (checkOrder == null){
            ORDERSERVICELOGGER.error("unable to getOrderByID");
            return null;
        }
        return checkOrder;
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create_profile')")
    public Order addOrder(HttpServletRequest request, @RequestBody Order order){
        ORDERSERVICELOGGER.info("Request came addOrder");
        order.setOrderDate(LocalDate.now());
        Order checkOrder = orderService.addOrder(order);
        if (checkOrder == null){
            ORDERSERVICELOGGER.error("unable to addOrder");
            return null;
        }
        return checkOrder;
    }

    @RequestMapping(value = "/deleteOrder/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('delete_profile')")
    public Order deleteOrderByID(@PathVariable int id){
        ORDERSERVICELOGGER.info("Request came deleteOrderByID");
        Order checkOrder = orderService.deleteOrderByID(id);
        if (checkOrder == null){
            ORDERSERVICELOGGER.error("unable to deleteOrderByID");
            return null;
        }
        return checkOrder;
    }


}
