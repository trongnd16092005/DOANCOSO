package com.example.demofx1.Data_Connection;

import java.time.LocalDateTime;

public class PaymentData {
    private int id;
    private String userID;
    private  int amount;
    private LocalDateTime date;

    public PaymentData(int id, String userID, int amount, LocalDateTime date) {
        this.id = id;
        this.userID = userID;
        this.amount = amount;
        this.date = date;
    }

    public PaymentData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
