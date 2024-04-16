package com.example.demofx1.Data_Connection;

public class ProductData {
    private int ID;
    private String name;
    private String manufacturer;
    private int quantity;
    private int price;
    private String status;

    public ProductData(int ID, String name, String manufacturer, int quantity, int price) {
        this.ID = ID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
    }

    public ProductData(int ID, String name, String manufacturer, int price, String status) {
        this.ID = ID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProductData() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
