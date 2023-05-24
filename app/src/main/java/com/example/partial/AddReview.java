package com.example.partial;

public class AddReview {
    String reviewID;
    String productName;
    String reviewDesc;
    String reviewRate;
    String storePurchased;
    String recommendIt;

    public AddReview() {
    }

    public AddReview(String reviewID, String productName, String reviewDesc, String reviewRate, String storePurchased, String recommendIt) {
        this.reviewID = reviewID;
        this.productName = productName;
        this.reviewDesc = reviewDesc;
        this.reviewRate = reviewRate;
        this.storePurchased = storePurchased;
        this.recommendIt = recommendIt;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }

    public String getReviewRate() {
        return reviewRate;
    }

    public void setReviewRate(String reviewRate) {
        this.reviewRate = reviewRate;
    }

    public String getStorePurchased() {
        return storePurchased;
    }

    public void setStorePurchased(String storePurchased) {
        this.storePurchased = storePurchased;
    }

    public String getRecommendIt() {
        return recommendIt;
    }

    public void setRecommendIt(String recommendIt) {
        this.recommendIt = recommendIt;
    }
}
