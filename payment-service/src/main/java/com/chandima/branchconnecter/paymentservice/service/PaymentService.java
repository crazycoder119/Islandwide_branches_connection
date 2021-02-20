package com.chandima.branchconnecter.paymentservice.service;


import com.chandima.branchconnector.commons.model.paymentservice.Payment;

public interface PaymentService {
    Payment getPaymentByOrderID(int id);

    Payment addPayment(Payment payment);

    Payment updatePaymentByOrderID(Payment payment);

    Payment deletePayment(int id);

    Payment addInitialPayment(Payment payment);
}
