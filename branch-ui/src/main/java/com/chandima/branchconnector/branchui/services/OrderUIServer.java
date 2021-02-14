package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.commons.model.orderservice.Order;
import org.springframework.ui.Model;

public interface OrderUIServer {
    Model loadOrders(Model model);

    Model addOrder(Order order, Model model);
}
