package com.example.nzta_booking_app.models;



public class Instructor {
    private String instructorID;
    private String instructorFirstName;
    private String instructorLastName;
    private String instructorLicenceNum;
    private String instructorEmail;
    private String instructorPassword;

    public Instructor() {
    }

    public Instructor(String instructorID, String instructorFirstName, String instructorLastName, String instructorLicenceNum, String instructorEmail, String instructorPassword) {
        this.instructorID = instructorID;
        this.instructorFirstName = instructorFirstName;
        this.instructorLastName = instructorLastName;
        this.instructorLicenceNum = instructorLicenceNum;
        this.instructorEmail = instructorEmail;
        this.instructorPassword = instructorPassword;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public String getInstructorFirstName() {
        return instructorFirstName;
    }

    public String getInstructorLastName() {
        return instructorLastName;
    }

    public String getInstructorLicenceNum() {
        return instructorLicenceNum;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public String getInstructorPassword() {
        return instructorPassword;
    }

    public void setInstructorFirstName(String instructorFirstName) {
        this.instructorFirstName = instructorFirstName;
    }

    public void setInstructorLastName(String instructorLastName) {
        this.instructorLastName = instructorLastName;
    }

    public void setInstructorLicenceNum(String instructorLicenceNum) {
        this.instructorLicenceNum = instructorLicenceNum;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public void setInstructorPassword(String instructorPassword) {
        this.instructorPassword = instructorPassword;
    }
    public String instructorFullName() {
        return instructorFirstName+" "+instructorLastName;
    }
}