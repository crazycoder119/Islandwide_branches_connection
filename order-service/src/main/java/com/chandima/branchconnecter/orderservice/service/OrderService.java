package com.chandima.branchconnecter.orderservice.service;

import com.chandima.branchconnector.commons.model.orderservice.Order;

import java.util.List;

public interface OrderService {
    Order addOrder(Order order);

    Order getOrderByID(int id);

    Order deleteOrderByID(int id);

    Order updateOrderByID(Order order);

    List<Order> getOrders();
}
