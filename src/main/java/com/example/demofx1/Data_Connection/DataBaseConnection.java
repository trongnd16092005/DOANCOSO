package com.example.demofx1.Data_Connection;


import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public Connection databaseLink;
    public Connection getConnection(){
        String databaseName="appmuaban";
        String databaseAdmin="root";
        String databasePasswordAd="qazqaz123123";
        String urlAdmin="jdbc:mysql://192.168.111.123/"+databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink= DriverManager.getConnection(urlAdmin,databaseAdmin,databasePasswordAd);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

}
