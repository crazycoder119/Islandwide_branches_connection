package com.chandima.branchconnecter.productservice.service;

import com.chandima.branchconnector.commons.model.productservice.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);

    Product getProductByID(int id);

    Product deleteProductByID(int id);

    List<Product> getAllProducts();

    Product updateProductByID(Product product);

    Product updateKandyStock(Product product);

    Product updateColomboStock(Product product);

    Product updateGalleStock(Product product);
}
