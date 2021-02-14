package com.chandima.branchconnecter.customerservice.service;

import com.chandima.branchconnector.commons.model.customerservice.Customer;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer customer);

   Customer getCustomerByID(int id);

    Customer deleteCustomerByID(int id);

    Customer updateCustomerByID(Customer customer);

    List<Customer> getAllCustomers();
}
