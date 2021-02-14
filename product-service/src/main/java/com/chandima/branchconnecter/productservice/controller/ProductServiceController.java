package com.chandima.branchconnecter.productservice.controller;

import com.chandima.branchconnecter.productservice.service.ProductService;
import com.chandima.branchconnector.commons.model.productservice.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/services")
public class ProductServiceController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/getAllProducts" , method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('create_profile')")
    public Product addProduct(@RequestBody Product product){
        Product checkProduct = productService.addProduct(product);
        if(checkProduct==null){
            return null;
        }
        return checkProduct;
    }

    @RequestMapping(value = "/getProduct/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Product getProductByID(@PathVariable int id){
        Product checkProduct = productService.getProductByID(id);
        if(checkProduct==null){
            return null;
        }
        return checkProduct;
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('update_profile')")
    public  Product updateProductByID(@RequestBody Product product){
        Product checkProduct = productService.updateProductByID(product);
        if(checkProduct==null){
            return null;
        }
        return checkProduct;
    }

    @RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('delete_profile')")
    public Product deleteProductByID(@PathVariable int id){
        Product checkProduct = productService.deleteProductByID(id);
        if(checkProduct==null){
            return null;
        }
        return checkProduct;
    }

    @RequestMapping(value = "/updateKandyStock", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('kandy_access')")
    public Product updateKandyStock(@RequestBody Product product){
        Product checkProduct = productService.updateKandyStock(product);
        if(checkProduct==null){
            return null;
        }
        return checkProduct;
    }

    @RequestMapping(value = "/updateColomboStock", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('colombo_access')")
    public Product updateColomboStock(@RequestBody Product product){
        Product checkProduct = productService.updateColomboStock(product);
        if(checkProduct==null){
            return null;
        }
        return checkProduct;
    }

    @RequestMapping(value = "/updateGalleStock", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('galle_access')")
    public Product updateGalleStock(@RequestBody Product product){
        Product checkProduct = productService.updateGalleStock(product);
        if(checkProduct==null){
            return null;
        }
        return checkProduct;
    }

}
