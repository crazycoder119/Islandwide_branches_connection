package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import com.chandima.branchconnector.commons.model.productservice.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

public interface DeliveryUIServer {
    Model loadDeliveries(Model model);

    Model addDelivery(Delivery delivery, Model model);
}
