package com.example.nzta_booking_app.models;

public class Booking {
    private String bookingID;
    private String bookingDate;
    private String bookingTime;
    private String user;
    private String instructor;
    private Boolean isResulted;
    private String results;
    private String comments;

    public Booking(String bookingID, String bookingDate, String bookingTime, String user, String instructor, Boolean isResulted, String results, String comments) {
        this.bookingID = bookingID;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.user = user;
        this.instructor = instructor;
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

    public String getBookingUser() {
        return user;
    }

    public String getBookingInstructor() {
        return instructor;
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

    public void setBookingUser(String userID) {
        this.user = user;
    }

    public void setBookingInstructor(String instructorID) {
        this.instructor = instructor;
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
