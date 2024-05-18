package com.example.demofx1.Data_Connection;


import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public Connection databaseLink;
    public Connection getConnection(){
        String databaseName="appmuaban";
        String databaseAdmin="root";
        String databasePasswordAd="1234";
        String serverIP = "35.232.248.37";

        String url = "jdbc:mysql://" + serverIP + "/" + databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink= DriverManager.getConnection(url,databaseAdmin,databasePasswordAd);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

}
