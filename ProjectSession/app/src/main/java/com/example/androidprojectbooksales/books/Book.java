package com.example.androidprojectbooksales.books;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;

public class Book {

    @SerializedName("id")
    String id;
    @SerializedName("title")
    String title;
    @SerializedName("author")
    String author;
    @SerializedName("category")
    String category;
    @SerializedName("description")
    String description;
    @SerializedName("available")
    int available;
    @SerializedName("price")
    double price;
    @SerializedName("owner")
    String owner;
    @SerializedName("rentedBy")
    String rentedBy;

    public Book(String id, String title, String author, String category, String description, int available, double price, String owner, String rentedBy) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.available = available;
        this.price = price;
        this.owner = owner;
        this.rentedBy = rentedBy;
    }

    public Book(String title, String author, String category, String description, int available, double price, String owner, String rentedBy) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.description = description;
        this.available = available;
        this.price = price;
        this.owner = owner;
        this.rentedBy = rentedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRentedBy() {
        return rentedBy;
    }

    public void setRentedBy(String rentedBy) {
        this.rentedBy = rentedBy;
    }
}
