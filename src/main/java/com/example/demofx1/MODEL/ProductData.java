package com.example.demofx1.MODEL;

public class ProductData {
    private int ID;
    private String name;
    private String manufacturer;
    private int quantity;
    private int price;
    private String image;

    public ProductData(int ID, String name, String manufacturer, int quantity, int price, String image) {
        this.ID = ID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
    }

    public ProductData(int ID, String name, String manufacturer, int quantity, int price) {
        this(ID, name, manufacturer, quantity, price, null);
    }

    public ProductData(int ID, String name, String manufacturer, int price, String image) {
        this(ID, name, manufacturer, 0, price, image);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
