package com.chandima.branchconnecter.productservice.repository;

import com.chandima.branchconnector.commons.model.productservice.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductServiceRepository extends JpaRepository<Product, Integer> {
}
