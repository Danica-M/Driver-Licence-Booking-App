package com.example.nzta_booking_app.models;

public class Booking {
    private String bookingID;
    private String bookingDate;
    private String bookingTime;
    private String userID;
    private String InstructorID;
    private Boolean isResulted;
    private String results;
    private String comments;

    public Booking(String bookingID, String bookingDate, String bookingTime, String userID, String instructorID, Boolean isResulted, String results, String comments) {
        this.bookingID = bookingID;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.userID = userID;
        InstructorID = instructorID;
        this.isResulted = isResulted;
        this.results = results;
        this.comments = comments;
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getUserID() {
        return userID;
    }

    public String getInstructorID() {
        return InstructorID;
    }

    public Boolean getResulted() {
        return isResulted;
    }

    public String getResults() {
        return results;
    }

    public String getComments() {
        return comments;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setInstructorID(String instructorID) {
        InstructorID = instructorID;
    }

    public void setResulted(Boolean resulted) {
        isResulted = resulted;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
