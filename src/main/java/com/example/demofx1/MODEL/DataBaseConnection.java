package com.example.demofx1.MODEL;


import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public Connection databaseLink;
    public Connection getConnection(){
        String databaseName="appmuaban";
        String databaseAdmin="root";
        String databasePasswordAd="1234";

        String PasswordAd="qazqaz123123";
        String serverIP = "127.0.0.3";

        String url1 = "jdbc:mysql://" + serverIP + "/" + databaseName;
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//            databaseLink= DriverManager.getConnection(url1,databaseAdmin,databasePasswordAd);
            databaseLink= DriverManager.getConnection(url1,databaseAdmin,PasswordAd);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

}
