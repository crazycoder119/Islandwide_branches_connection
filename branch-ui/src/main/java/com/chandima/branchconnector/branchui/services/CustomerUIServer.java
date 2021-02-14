package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.commons.model.customerservice.Customer;
import org.springframework.ui.Model;

public interface CustomerUIServer {

    Model loadCustomer(Model model);

    Model addCustomer(Customer customer, Model model);
}
