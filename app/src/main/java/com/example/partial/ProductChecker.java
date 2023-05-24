package com.example.partial;

import com.google.firebase.Timestamp;

public class ProductChecker {
    String prodName;
    String prodAvailable;
    String storeAvailable;
    Timestamp timestamp;
    public ProductChecker() {
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdAvailable() {
        return prodAvailable;
    }

    public void setProdAvailable(String prodAvailable) {
        this.prodAvailable = prodAvailable;
    }

    public String getStoreAvailable() {
        return storeAvailable;
    }

    public void setStoreAvailable(String storeAvailable) {
        this.storeAvailable = storeAvailable;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
