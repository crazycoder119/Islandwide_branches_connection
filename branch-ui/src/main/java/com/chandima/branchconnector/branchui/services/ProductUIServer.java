package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.commons.model.customerservice.Customer;
import com.chandima.branchconnector.commons.model.productservice.Product;
import org.springframework.ui.Model;

public interface ProductUIServer {

    Model loadProducts(Model model);

    Model addProduct(Product product, Model model);

    Model updateColomboStock(Product product, Model model);

    Model kandyStockQuantity(Product product, Model model);

    Model galleStockQuantity(Product product, Model model);
}
