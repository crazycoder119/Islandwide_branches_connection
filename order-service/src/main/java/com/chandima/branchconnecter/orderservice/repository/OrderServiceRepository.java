package com.chandima.branchconnecter.orderservice.repository;

import com.chandima.branchconnector.commons.model.orderservice.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderServiceRepository extends JpaRepository<Order, Integer> {
}
