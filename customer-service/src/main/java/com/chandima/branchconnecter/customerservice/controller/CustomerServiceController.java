package com.chandima.branchconnecter.customerservice.controller;

import com.chandima.branchconnecter.customerservice.service.CustomerService;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/services")
public class CustomerServiceController {
    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/getAllCustomers" , method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public  List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('update_profile')")
    public Customer addCustomer(@RequestBody Customer customer){
        Customer checkCustomer = customerService.addCustomer(customer);
        if(checkCustomer==null){
            //Add warning trying to save the customer with excisting id.
            return null;
        }
        //Add info log customer is saved +customer.getid();
        return checkCustomer;
    }

    @RequestMapping(value = "/getCustomer/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('read_profile')")
    public Customer getCustomerByID(@PathVariable int id){
        System.out.println("came to customer service" +id);
        Customer checkCustomer = customerService.getCustomerByID(id);
        if (checkCustomer == null){
            return null;
        }
        return checkCustomer;
    }

    @RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('update_profile')")
    public Customer updateCustomerByID(@RequestBody Customer customer){
        Customer checkCustomer = customerService.updateCustomerByID(customer);
        if(checkCustomer == null){
            return null;
        }
        return checkCustomer;
    }


    @RequestMapping(value = "/deleteCustomer/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('delete_profile')")
    public Customer deleteCustomerByID(@PathVariable int id){
        Customer checkCustomer =  customerService.deleteCustomerByID(id);
        if (checkCustomer == null){
            return null;
        }
        return checkCustomer;
    }

}
