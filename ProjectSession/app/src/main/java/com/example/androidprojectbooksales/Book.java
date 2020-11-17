package com.example.androidprojectbooksales;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("id")
    String id;
    @SerializedName("titleBook")
    String title;
    @SerializedName("authorBook")
    String author;
    @SerializedName("categoryBook")
    String category;
    @SerializedName("summaryBook")
    String summary;
    @SerializedName("availbleBook")
    int available;
    @SerializedName("priceBook")
    double price;
    @SerializedName("ownerBook")
    String owner;
    @SerializedName("rentedByBook")
    String rentedBy;

    public Book(String id, String title, String author, String category, String summary, int available, double price, String owner, String rentedBy) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.summary = summary;
        this.available = available;
        this.price = price;
        this.owner = owner;
        this.rentedBy = rentedBy;
    }

    public Book(String title, String author, String category, String summary, int available, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.summary = summary;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
