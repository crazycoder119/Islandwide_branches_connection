package com.chandima.branchconnecter.paymentservice.service;

import com.chandima.branchconnecter.paymentservice.repository.PaymentServiceRepository;
import com.chandima.branchconnector.commons.model.paymentservice.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.DefaultEditorKit;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentServiceRepository paymentServiceRepository;

    @Override
    public Payment getPaymentByOrderID(int id) {
        Optional<Payment> checkPayment = paymentServiceRepository.getPaymentByOrderID(id);
        if (checkPayment.isPresent()){
            return checkPayment.get();
        }
        return null;
    }

    @Override
    public Payment addPayment(Payment payment) {
        Payment checkPayment = getPaymentByID(payment.getId());
        if (payment!=null){
            return paymentServiceRepository.save(payment);
        }
        return null;
    }

    @Override
    public Payment updatePaymentByOrderID(Payment payment) {
        Payment existPayment = getPaymentByOrderID(payment.getOrderID());
        if(existPayment!=null){
            if(payment.getOrderCost()!=null){
                existPayment.setOrderCost(payment.getOrderCost());
            }
            if(payment.getDeliveryCost()!=null){
                existPayment.setDeliveryCost(payment.getDeliveryCost());
            }
            if (payment.getTotalCost()!=null){
                existPayment.setTotalCost(payment.getTotalCost());
            }
            if(payment.getPayedAmount()!=null){
                existPayment.setPayedAmount(existPayment.getPayedAmount().add(payment.getPayedAmount()));
            }
            existPayment.setTotalCost(payment.getTotalCost());
            existPayment.setBalance(existPayment.getTotalCost().subtract(existPayment.getPayedAmount()));
            return paymentServiceRepository.save(existPayment);
        }
        return null;
    }

    @Override
    public Payment deletePayment(int id) {
        Payment existPayment = getPaymentByID(id);
        if (existPayment!=null){
            paymentServiceRepository.deleteById(id);
            return existPayment;
        }
        return null;
    }

    private Payment getPaymentByID(int id){
        Optional<Payment> payment = paymentServiceRepository.findById(id);
        if(payment.isPresent()){
            return  payment.get();
        }
        return null;
    }
}
