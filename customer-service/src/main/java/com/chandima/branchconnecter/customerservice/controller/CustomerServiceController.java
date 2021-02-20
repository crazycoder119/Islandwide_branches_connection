package com.chandima.branchconnecter.customerservice.controller;

import com.chandima.branchconnecter.customerservice.service.CustomerService;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/services")
public class CustomerServiceController {
    @Autowired
    CustomerService customerService;

    private static final Logger CUSTOMERSERVICELOGGER = LoggerFactory.getLogger(CustomerServiceController.class);

    @RequestMapping(value = "/getAllCustomers" , method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public  List<Customer> getAllCustomers(){
        CUSTOMERSERVICELOGGER.info("Request came getAllCustomers");
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('update_profile')")
    public Customer addCustomer(@RequestBody Customer customer){
        CUSTOMERSERVICELOGGER.info("Request came addCustomer");
        Customer checkCustomer = customerService.addCustomer(customer);
        if(checkCustomer==null){
            CUSTOMERSERVICELOGGER.error("Error Customer object is not saved in class : Customer-service.CustomerServiceController");
            return null;
        }
        //Add info log customer is saved +customer.getid();
        return checkCustomer;
    }

    @RequestMapping(value = "/getCustomer/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Customer getCustomerByID(@PathVariable int id){
        CUSTOMERSERVICELOGGER.info("Request came getCustomerByID");
        Customer checkCustomer = customerService.getCustomerByID(id);
        if (checkCustomer == null){
            CUSTOMERSERVICELOGGER.error("unable to getCustomerByID");
            return null;
        }
        return checkCustomer;
    }

    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('update_profile')")
    public Customer updateCustomerByID(@RequestBody Customer customer){
        CUSTOMERSERVICELOGGER.info("Request came updateCustomerByID");
        Customer checkCustomer = customerService.updateCustomerByID(customer);
        if(checkCustomer == null){
            CUSTOMERSERVICELOGGER.error("unable to updateCustomerByID");
            return null;
        }
        return checkCustomer;
    }


    @RequestMapping(value = "/deleteCustomer/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('delete_profile')")
    public Customer deleteCustomerByID(@PathVariable int id){
        CUSTOMERSERVICELOGGER.info("Request came deleteCustomerByID");
        Customer checkCustomer =  customerService.deleteCustomerByID(id);
        if (checkCustomer == null){
            CUSTOMERSERVICELOGGER.error("unable to deleteCustomerByID");
            return null;
        }
        return checkCustomer;
    }

}
