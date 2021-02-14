package com.chandima.branchconnector.commons.model.paymentservice;

import javax.persistence.*;
import java.math.BigInteger;


@Entity
@Table(name = "payments")
public class Payment {
    @Id
    int id;

    int orderID;
    int deliverID;
    BigInteger orderCost;
    BigInteger deliveryCost;
    BigInteger totalCost;
    BigInteger payedAmount;
    BigInteger balance;

    public int getDeliverID() {
        return deliverID;
    }

    public void setDeliverID(int deliverID) {
        this.deliverID = deliverID;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public BigInteger getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigInteger orderCost) {
        this.orderCost = orderCost;
    }

    public BigInteger getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigInteger deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigInteger getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigInteger totalCost) {
        this.totalCost = totalCost;
    }

    public BigInteger getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(BigInteger payedAmount) {
        this.payedAmount = payedAmount;
    }
}
