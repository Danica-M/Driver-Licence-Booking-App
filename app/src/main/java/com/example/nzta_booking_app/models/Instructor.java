package com.example.nzta_booking_app.models;

import java.io.Serializable;

public class Instructor implements Serializable {
    private String instructorID;
    private String fName;
    private String lName;
    private String licenceNum;
    private String email;
    private String password;

    public Instructor() {
    }

    public Instructor(String instructorID, String fName, String lName, String licenceNum, String email, String password) {
        this.instructorID = instructorID;
        this.fName = fName;
        this.lName = lName;
        this.licenceNum = licenceNum;
        this.email = email;
        this.password = password;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
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

    public void setFName(String fName) {
        this.fName = fName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
