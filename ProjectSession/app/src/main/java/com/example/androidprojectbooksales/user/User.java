package com.example.androidprojectbooksales.user;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    String id;
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("email")
    String email;
    @SerializedName("phone")
    String phone;
    @SerializedName("password")
    String password;
    @SerializedName("adress")
    String adress;
    @SerializedName("ext")
    String ext="png";

    public User(String id, String firstName, String lastName, String email, String phone, String password, String adress, String ext) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.adress = adress;
        this.ext = ext;
    }

    public User(String id, String firstName, String lastName, String email, String phone, String password, String adress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.adress = adress;
        this.ext="png";
    }

    public User() {
        this.ext="png";
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String adress) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
