package com.example.nzta_booking_app.models;

public class Instructor {
    private String instructorID;
    private String fName;
    private String lName;
    private String licenceNum;
    private String email;
    private String password;

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

    public String getfName() {
        return fName;
    }

    public String getlName() {
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
