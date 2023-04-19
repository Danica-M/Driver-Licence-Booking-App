package com.example.nzta_booking_app.models;

public class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String licenceNum;
    private String email;
    private String password;

    public User() {
    }

    public User(String userID, String firstName, String lastName, String licenceNum, String email, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenceNum = licenceNum;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLicenceNum() {
        return licenceNum;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLicenceNum(String licenceNum) {
        this.licenceNum = licenceNum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String userFullName() {
        return firstName + " " + lastName;
    }
}