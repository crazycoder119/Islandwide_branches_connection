package com.chandima.branchconnector.commons.model.orderservice;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    int id;

    int customerID;
    int productID;
    int quantity;
    LocalDate orderDate;
    BigInteger orderCost;

    public BigInteger getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigInteger orderCost) {
        this.orderCost = orderCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

}
