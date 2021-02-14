package com.chandima.branchconnecter.customerservice.repository;

import com.chandima.branchconnector.commons.model.customerservice.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerServiceRepository extends JpaRepository<Customer, Integer> {
}
