package com.chandima.branchconnecter.productservice.service;

import com.chandima.branchconnecter.productservice.repository.ProductServiceRepository;
import com.chandima.branchconnector.commons.model.productservice.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductServiceRepository productServiceRepository;

    @Override
    public Product addProduct(Product product) {
        if(getProductByID(product.getId())==null){
            return productServiceRepository.save(product);
        }
        return  null;
    }

    @Override
    public Product getProductByID(int id) {
        Optional<Product> product = productServiceRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        return null;
    }

    @Override
    public Product deleteProductByID(int id) {
        Product product = getProductByID(id);
        if(product!=null){
            productServiceRepository.deleteById(id);
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productServiceRepository.findAll();
    }

    @Override
    public Product updateProductByID(Product product) {
        Product existProduct = getProductByID(product.getId());
        if(existProduct!=null){
            if (product.getProductName()!=null){
                existProduct.setProductName(product.getProductName());
            }
            if (product.getProductDescription()!=null){
                existProduct.setProductDescription(product.getProductDescription());
            }
            if (product.getPrice()!=null){
                existProduct.setPrice(product.getPrice());
            }
            return productServiceRepository.save(existProduct);
        }
        return null;
    }

    @Override
    public Product updateKandyStock(Product product) {
        Product existProduct = getProductByID(product.getId());
        if(existProduct!=null){
            if(product.getKandyStockQuantity()>0){
                existProduct.setKandyStockQuantity(product.getKandyStockQuantity());
                return  productServiceRepository.save(existProduct);
            }
        }
        return null;
    }

    @Override
    public Product updateColomboStock(Product product) {
        Product existProduct = getProductByID(product.getId());
        if(existProduct!=null){
            if(product.getColomboStockQuantity()>0){
                existProduct.setColomboStockQuantity(product.getColomboStockQuantity());
                return  productServiceRepository.save(existProduct);
            }
        }
        return null;
    }

    @Override
    public Product updateGalleStock(Product product) {
        Product existProduct = getProductByID(product.getId());
        if(existProduct!=null){
            if(product.getGalleStockQuantity()>0){
                existProduct.setGalleStockQuantity(product.getGalleStockQuantity());
                return  productServiceRepository.save(existProduct);
            }
        }
        return null;
    }

}
