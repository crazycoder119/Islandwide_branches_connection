package com.chandima.branchconnector.branchui.services;

import com.chandima.branchconnector.commons.model.paymentservice.Payment;
import org.springframework.ui.Model;

public interface PaymentUIServer {

    Model getPayment(Payment payment,Model model);

    Model addPayment(Payment payment, Model model);
}
