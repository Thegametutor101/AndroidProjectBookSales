package com.example.androidprojectbooksales;

import com.google.gson.annotations.SerializedName;

public class Rentals {

    @SerializedName("id")
    String id;
    @SerializedName("bookID")
    String bookID;
    @SerializedName("userID")
    String userID;

    public Rentals(String id, String bookID, String userID) {
        this.id = id;
        this.bookID = bookID;
        this.userID = userID;
    }

    public Rentals(String bookID, String userID) {
        this.bookID = bookID;
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
