package com.example.demofx1.Data_Connection;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBaseConnection {
    public Connection databaseLink;
    public Connection getConnection(){
        String databaseName="appmuaban";
        String databaseAdmin="root";
        String databasePasswordAd="qazqaz123123";
        String urlAdmin="jdbc:mysql://localhost/"+databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink= DriverManager.getConnection(urlAdmin,databaseAdmin,databasePasswordAd);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

}