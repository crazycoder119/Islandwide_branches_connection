package com.chandima.branchconnector.commons.model.productservice;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "products")
public class Product {
    @Id
    int id;

    String productName;
    String productDescription;
    BigInteger price;
    int kandyStockQuantity;
    int colomboStockQuantity;
    int galleStockQuantity;

    public int getKandyStockQuantity() {
        return kandyStockQuantity;
    }

    public void setKandyStockQuantity(int kandyStockQuantity) {
        this.kandyStockQuantity = kandyStockQuantity;
    }

    public int getColomboStockQuantity() {
        return colomboStockQuantity;
    }

    public void setColomboStockQuantity(int colomboStockQuantity) {
        this.colomboStockQuantity = colomboStockQuantity;
    }

    public int getGalleStockQuantity() {
        return galleStockQuantity;
    }

    public void setGalleStockQuantity(int galleStockQuantity) {
        this.galleStockQuantity = galleStockQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }
}
