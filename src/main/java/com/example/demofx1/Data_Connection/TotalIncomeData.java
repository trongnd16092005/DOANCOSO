package com.example.demofx1.Data_Connection;

import java.time.LocalDateTime;

public class TotalIncomeData {
    private String paymentID;
    private String userID;
    private int amount;
    private LocalDateTime dateTime;
    public TotalIncomeData(String paymentID,String userID,int amount,LocalDateTime dateTime){
        this.paymentID=paymentID;
        this.userID=userID;
        this.dateTime=dateTime;
        this.amount=amount;
    }
    public TotalIncomeData(){
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
