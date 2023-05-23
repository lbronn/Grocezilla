package com.example.partial;

public class PurchaseHistory {
    String purchaseTitle;
    String totalExpenses;
    String purchaseDescription;

    public PurchaseHistory() {
    }

    public String getPurchaseTitle() {
        return purchaseTitle;
    }

    public void setPurchaseTitle(String purchaseTitle) {
        this.purchaseTitle = purchaseTitle;
    }

    public String getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getPurchaseDescription() {
        return purchaseDescription;
    }

    public void setPurchaseDescription(String purchaseDescription) {
        this.purchaseDescription = purchaseDescription;
    }
}
