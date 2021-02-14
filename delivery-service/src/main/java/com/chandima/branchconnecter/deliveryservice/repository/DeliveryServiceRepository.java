package com.chandima.branchconnecter.deliveryservice.repository;

import com.chandima.branchconnector.commons.model.deliveryservice.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface DeliveryServiceRepository extends JpaRepository<Delivery, Integer> {

    @Transactional
    @Query(value = "SELECT * FROM delivery_service.deliverys where delivery_service.deliverys.orderID =?1", nativeQuery = true)
    Optional<Delivery> getDeliveryByOrderID(int id);
}
