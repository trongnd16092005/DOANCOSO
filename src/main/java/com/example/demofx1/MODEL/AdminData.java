package com.example.demofx1.MODEL;

public class AdminData {
    private String adminName;
    private String FName;

    public AdminData(String adminName,String FName){
        this.FName=FName;
        this.adminName=adminName;
    }

    public String getUserName() {
        return adminName;
    }

    public void setUserName(String userName) {
        this.adminName = userName;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }}
