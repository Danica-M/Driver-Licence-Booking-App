package com.example.nzta_booking_app.models;

import java.io.Serializable;

public class User implements Serializable {
    private String userID;
    private String fName;
    private String lName;
    private String licenceNum;
    private String email;
    private String password;

    public User() {
    }

    public User(String userID, String fName, String lName, String licenceNum, String email, String password) {
        this.userID = userID;
        this.fName = fName;
        this.lName = lName;
        this.licenceNum = licenceNum;
        this.email = email;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserFName() {
        return fName;
    }

    public String getUserLName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLicenceNum() {
        return licenceNum;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
