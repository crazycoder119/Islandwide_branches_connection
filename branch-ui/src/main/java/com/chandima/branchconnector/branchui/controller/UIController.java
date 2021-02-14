package com.chandima.branchconnector.branchui.controller;

import com.chandima.branchconnector.branchui.config.AccessToken;
import com.chandima.branchconnector.branchui.services.CustomerUIServer;
import com.chandima.branchconnector.branchui.services.DeliveryUIServer;
import com.chandima.branchconnector.branchui.services.OrderUIServer;
import com.chandima.branchconnector.branchui.services.ProductUIServer;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import com.chandima.branchconnector.commons.model.orderservice.Order;
import com.chandima.branchconnector.commons.model.productservice.Product;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;

@Controller
@EnableOAuth2Sso
public class UIController extends WebSecurityConfigurerAdapter {
    @LoadBalanced
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CustomerUIServer customerUIServer;

    @Autowired
    ProductUIServer productUIServer;

    @Autowired
    DeliveryUIServer deliveryUIServer;

    @Autowired
    OrderUIServer orderUIServer;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated();
    }

    @RequestMapping(value = "/")
    public String loadUI(){
        return "home";
    }

    @RequestMapping(value = "/secure")
    public String loadSecureUI(){
        return "secure";
    }

    @RequestMapping(value = "/customers")
    public String loadCustomersUI(Model model){
        model.addAttribute("customerList", new ArrayList<>());
        return "customers";
    }
    @RequestMapping(value = "/addCustomer")
    public String loadAddCustomerUI(Model model){
        model.addAttribute("customer",new Customer());
        return "addCustomer";
    }

    @RequestMapping(value = "/products")
    public String loadProductsUI(Model model){
        model.addAttribute("productList", new ArrayList<>());
        return "products";
    }
    @RequestMapping(value = "/addProduct")
    public String loadAddProductUI(Model model){
        model.addAttribute("product",new Product());
        return "addProduct";
    }

    @RequestMapping(value = "/deliverys")
    public String loadDeliverysUI(Model model){
        model.addAttribute("deliveryList", new ArrayList<>());
        return "deliverys";
    }
    @RequestMapping(value = "/addDelivery")
    public String loadAddDeliveryUI(Model model){
        model.addAttribute("delivery",new Delivery());
        return "addDelivery";
    }

    @RequestMapping(value = "/orders")
    public String loadOrdersUI(Model model){
        model.addAttribute("orderList", new ArrayList<>());
        return "orders";
    }
    @RequestMapping(value = "/addOrder")
    public String loadAddOrderyUI(Model model){
        model.addAttribute("order", new Order());
        return "addOrder";
    }

    @RequestMapping(value = "/updateColomboStock")
    public String updateColomboStock(Model model){
        model.addAttribute("product",new Product());
        return "updateColomboStock";
    }

    @RequestMapping(value = "/updateKandyStock")
    public String updateKandyStock(Model model){
        model.addAttribute("product",new Product());
        return "updateKandyStock";
    }

    @RequestMapping(value = "/updateGalleStock")
    public String updateGalleStock(Model model){
        model.addAttribute("product",new Product());
        return "updateGalleStock";
    }




    //Customer details service
    @RequestMapping(value = "/services/getAllCustomers")
    public String loadCustomers(Model model){
        model = customerUIServer.loadCustomer(model);
        return "customers";
    }

    @RequestMapping(value = "/services/addCustomer")
    public String addCustomers(@ModelAttribute("form_addcustomer") Customer customer,Model model){
        model = customerUIServer.addCustomer(customer, model);
        return "addCustomer";
    }

    //product details service
    @RequestMapping(value = "/services/getAllProducts")
    public String loadProducts(Model model){
        model = productUIServer.loadProducts(model);
        return "products";
    }
    @RequestMapping(value = "/services/addProduct")
    public String addProducts(@ModelAttribute Product product,Model model){
        model = productUIServer.addProduct(product, model);
        return "addProduct";
    }
    @RequestMapping(value = "/services/updateColomboStock")
    public String updateColomboStock(@ModelAttribute Product product,Model model){
        model = productUIServer.updateColomboStock(product, model);
        return "updateColomboStock";
    }
    @RequestMapping(value = "/services/updateKandyStock")
    public String kandyStockQuantity(@ModelAttribute Product product,Model model){
        model = productUIServer.kandyStockQuantity(product, model);
        return "updateKandyStock";
    }
    @RequestMapping(value = "/services/updateGalleStock")
    public String updateGalleStock(@ModelAttribute Product product,Model model){
        model = productUIServer.galleStockQuantity(product, model);
        return "updateGalleStock";
    }

    //Delivery details service
    @RequestMapping(value = "/services/getAllDeliverys")
    public String loadDeliveries(Model model){
        model = deliveryUIServer.loadDeliveries(model);
        return "deliverys";
    }
    @RequestMapping(value = "/services/addDelivery")
    public String addDelivery(@ModelAttribute Delivery delivery,Model model){
        model = deliveryUIServer.addDelivery(delivery, model);
        return "addDelivery";
    }

    //Order details service
    @RequestMapping(value = "/services/getAllOrders")
    public String loadOrders(Model model){
        model = orderUIServer.loadOrders(model);
        return "orders";
    }
    @RequestMapping(value = "/services/addOrder")
    public String addOrder(@ModelAttribute Order order,Model model){
        model = orderUIServer.addOrder(order, model);
        return "addOrder";
    }

}
