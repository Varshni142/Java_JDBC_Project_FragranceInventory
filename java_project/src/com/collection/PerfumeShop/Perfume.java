package com.collection.PerfumeShop;

public class Perfume {
    private int id;
    private String name;
    private String brand;
    private String fragranceType;
    private int price;
    private int quantity;

    public Perfume(String name, String brand, String fragranceType, int price, int quantity) {
        this.name = name;
        this.brand = brand;
        this.fragranceType = fragranceType;
        this.price = price;
        this.quantity = quantity;
    }

    public Perfume(int id, String name, String brand, String fragranceType, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.fragranceType = fragranceType;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFragranceType() {
        return fragranceType;
    }

    public void setFragranceType(String fragranceType) {
        this.fragranceType = fragranceType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Perfume{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", fragranceType='" + fragranceType + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
