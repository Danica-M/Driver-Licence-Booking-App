package com.example.nzta_booking_app.models;

public class Booking {
    private String bookingID;
    private String bookingDate;
    private String bookingTime;
    private String bookingUser;
    private String bookingUserDL;
    private String bookingInstructor;
    private Boolean isResulted;
    private String results;
    private String comments;

    public Booking() {
    }

    public Booking(String bookingID, String bookingDate, String bookingTime, String bookingUser,String bookingUserDL, String bookingInstructor, Boolean isResulted, String results, String comments) {
        this.bookingID = bookingID;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.bookingUser = bookingUser;
        this.bookingInstructor = bookingInstructor;
        this.isResulted = isResulted;
        this.results = results;
        this.comments = comments;
        this.bookingUserDL = bookingUserDL;
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
        return bookingUser;
    }

    public String getBookingInstructor() {
        return bookingInstructor;
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

    public String getBookingUserDL() {
        return bookingUserDL;
    }

    public void setBookingUserDL(String bookingUserDL) {
        this.bookingUserDL = bookingUserDL;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setBookingUser(String bookingUser) {
        this.bookingUser = bookingUser;
    }

    public void setBookingInstructor(String bookingInstructor) {
        this.bookingInstructor = bookingInstructor;
    }

    public void setResulted(Boolean resulted) {
        this.isResulted = resulted;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
