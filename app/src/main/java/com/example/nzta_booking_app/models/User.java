package com.example.nzta_booking_app.models;

public class User {
    private String userID;
    private String fName;
    private String lName;
    private String email;
    private String password;

    public User(String userID, String fName, String lName, String email, String password) {
        this.userID = userID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
