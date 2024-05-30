package com.example.demofx1.Data_Connection;

public class CustomerData {
    private String name;
    private String phoneNumber;
    private int totalSpent;

    public CustomerData(String name, String phoneNumber, int totalSpent) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.totalSpent = totalSpent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(int totalSpent) {
        this.totalSpent = totalSpent;
    }
}