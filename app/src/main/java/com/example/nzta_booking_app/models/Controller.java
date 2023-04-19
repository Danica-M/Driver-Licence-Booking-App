package com.example.nzta_booking_app.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    public FirebaseDatabase firebaseDB;
    private static DatabaseReference reference;
    private static User currentUser;
    private static Instructor currentInstructor;


    public Controller() {
        firebaseDB = FirebaseDatabase.getInstance();
        reference = firebaseDB.getReference();
    }

    public static DatabaseReference getReference() {
        return reference;
    }

    public static Instructor getCurrentInstructor() {
        return currentInstructor;
    }

    public static void setCurrentInstructor(Instructor instructor) {
        currentInstructor = instructor;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public User registerUser(String userID, String fName, String lName, String licenceNum, String email, String password) {
        try {
            User user = new User(userID, fName, lName, licenceNum, email, password);
            reference.child("users").child(userID).setValue(user);
            return user;

        } catch (Exception ex) {
            return null;
        }
    }

    public Instructor registerInstructor(String instructorID, String fName, String lName, String licenceNum, String email, String password) {
        try {
            Instructor instructor = new Instructor(instructorID, fName, lName, licenceNum, email, password);
            reference.child("instructors").child(instructorID).setValue(instructor);
            return instructor;

        } catch (Exception ex) {
            return null;
        }
    }


    public Booking bookTest(String bookingDate, String bookingTime, String user, String userDl, String instructor) {
        try {
            String bookingID = reference.push().getKey();
            Booking booking = new Booking(bookingID, bookingDate, bookingTime, user, userDl, instructor, false, "", "");
            reference.child("bookings").child(bookingID).setValue(booking);
            return booking;

        } catch (Exception ex) {
            return null;
        }
    }


    public boolean validateLicence(String licence) {
        Pattern pattern = Pattern.compile("^[A-Za-z]{2}\\d{6}$");
        Matcher matcher = pattern.matcher(licence);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


}
