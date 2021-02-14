package com.chandima.branchconnector.commons.model.deliveryservice;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "deliverys")
public class Delivery {
    @Id
    int id;

    int orderID;
    String pickUpLocation;
    String deliveryLocation;
    LocalDate orderDate;
    LocalDate estimateDate;
    Boolean doneStatus;
    BigInteger deliveryCost;

    public BigInteger getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigInteger deliveryCost) {
        this.deliveryCost = deliveryCost;
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

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(LocalDate estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Boolean getDoneStatus() {
        return doneStatus;
    }

    public void setDoneStatus(Boolean doneStatus) {
        this.doneStatus = doneStatus;
    }
}
