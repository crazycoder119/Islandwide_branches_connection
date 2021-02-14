package com.chandima.branchconnecter.customerservice.service;

import com.chandima.branchconnecter.customerservice.repository.CustomerServiceRepository;
import com.chandima.branchconnector.commons.model.customerservice.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerServiceRepository customerServiceRepository;


    @Override
    public Customer addCustomer(Customer customer) {
        if(getCustomerByID(customer.getId())==null){
            return customerServiceRepository.save(customer);
        }
            return null;
    }

    @Override
    public Customer getCustomerByID(int id) {
        Optional<Customer> customer = customerServiceRepository.findById(id);
        if(customer.isPresent()){
            return customer.get();
        }
        return null;
    }

    @Override
    public Customer deleteCustomerByID(int id) {
        Customer customer = getCustomerByID(id);
        if(customer != null){
            customerServiceRepository.deleteById(id);
            return  customer;
        }
       return null;
    }

    @Override
    public Customer updateCustomerByID(Customer customer) {
        Customer existingCustomer = getCustomerByID(customer.getId());
        if(existingCustomer!=null){
            if (customer.getCustomerName()!=null){
                existingCustomer.setCustomerName(customer.getCustomerName());
            }
            if(customer.getCustomerAddress()!=null){
                existingCustomer.setCustomerAddress(customer.getCustomerAddress());
            }
            if(customer.getCustomerEmail()!=null){
                existingCustomer.setCustomerEmail(customer.getCustomerEmail());
            }
            if (customer.getCustomerTelephone()!=null){
                existingCustomer.setCustomerTelephone(customer.getCustomerTelephone());
            }
            return customerServiceRepository.save(existingCustomer);
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerServiceRepository.findAll();
    }
}
