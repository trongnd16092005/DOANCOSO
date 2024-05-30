package com.example.demofx1.MODEL;

public class UserData {

    private String userName;
    private String FName;

    public UserData(String userName,String FName){
        this.FName=FName;
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }
}
