package com.chandima.branchconnecter.orderservice.service;

import com.chandima.branchconnecter.orderservice.repository.OrderServiceRepository;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderServiceRepository orderServiceRepository;

    @Override
    public Order addOrder(Order order) {
        Order checkOrder = getOrderByID(order.getId());
        if(checkOrder==null){
            order.setOrderDate(LocalDate.now());
            return orderServiceRepository.save(order);
        }
        return null;
    }

    @Override
    public Order getOrderByID(int id) {
        Optional<Order> checkOrder = orderServiceRepository.findById(id);
        if(checkOrder.isPresent()){
            return checkOrder.get();
        }
        return null;
    }

    @Override
    public Order deleteOrderByID(int id) {
        Order order = getOrderByID(id);
        if(order!=null){
            orderServiceRepository.deleteById(id);
        }
        return order;
    }

    @Override
    public Order updateOrderByID(Order order) {
        Order existOrder = getOrderByID(order.getId());
        if (existOrder!=null){
            if(order.getCustomerID()>0){
                existOrder.setCustomerID(order.getCustomerID());
            }
            if(order.getProductID()>0){
                existOrder.setProductID(order.getProductID());
            }
            if (order.getQuantity()>0){
                existOrder.setQuantity(order.getQuantity());
            }
            if(order.getOrderCost()!=null){
                existOrder.setOrderCost(order.getOrderCost());
            }
            return orderServiceRepository.save(existOrder);
        }
        return null;
    }

    @Override
    public List<Order> getOrders() {
        return orderServiceRepository.findAll();
    }
}
