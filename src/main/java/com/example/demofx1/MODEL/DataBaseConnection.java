package com.example.demofx1.MODEL;


import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public Connection databaseLink;
    public Connection getConnection(){
        String databaseName="appmuaban";
        String databaseAdmin="root";

        String PasswordAd="qazqaz123123";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Thiết lập kết nối tới cơ sở dữ liệu
            databaseLink= DriverManager.getConnection(url,databaseAdmin,PasswordAd);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

}
