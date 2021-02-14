package com.chandima.branchconnecter.paymentservice.repository;

import com.chandima.branchconnector.commons.model.paymentservice.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface PaymentServiceRepository extends JpaRepository<Payment, Integer> {

    @Transactional
    @Query(value = "SELECT * FROM payment_service.payments where payment_service.payments.orderID =?1", nativeQuery = true)
    Optional<Payment> getPaymentByOrderID(int id);
}
