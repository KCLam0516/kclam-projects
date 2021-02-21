package com.example.finalyearproject.data;

import com.google.firebase.database.Exclude;

public class Payment {
    private String paymentId;
    private String payName;
    private String payDate;
    private String ownerName;
    private String amount;


    public Payment(String payName, String payDate, String ownerName, String amount) {
        this.payName = payName;
        this.payDate = payDate;
        this.ownerName = ownerName;
        this.amount = amount;
    }

    public Payment() {}

    public String getPayName() {
        return payName;
    }

    public String getPayDate() {
        return payDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAmount() {
        return amount;
    }

    @Exclude
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) { this.paymentId=paymentId; }
}
