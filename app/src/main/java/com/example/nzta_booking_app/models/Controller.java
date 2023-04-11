package com.example.nzta_booking_app.models;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Controller {
    private FirebaseDatabase firebaseDB;
    private static DatabaseReference reference;
    private static User currentUser;
    private static Instructor currentInstructor;

    public Controller(){
        firebaseDB = FirebaseDatabase.getInstance();
        reference = firebaseDB.getReference();

    }

    public static DatabaseReference getReference() {
        return reference;
    }


    public static Instructor instructor;

    public static Instructor getCurrentInstructor() {
        return currentInstructor;
    }

    public static void setCurrentInstructor(Instructor currentInstructor) {
        Controller.currentInstructor = currentInstructor;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
    public User registerUser(String userID, String fName, String lName, String licenceNum, String email, String password){
        try{
//            String userID = reference.push().getKey();
            User user = new User(userID,fName, lName,licenceNum,email,password);
            reference.child("users").child(userID).setValue(user);
            return user;

        }catch (Exception ex){
            return null;
        }
    }

    public Instructor registerInstructor(String instructorID,String fName, String lName, String licenceNum, String email, String password){
        try{
//            String instructorID = reference.push().getKey();
            Instructor instructor = new Instructor(instructorID,fName, lName, licenceNum, email, password);
            reference.child("instructors").child(instructorID).setValue(instructor);
            return instructor;

        }catch (Exception ex){
            return null;
        }
    }


    public Booking bookTest(String bookingDate, String bookingTime, String user, String instructor){
        try{
            String bookingID = reference.push().getKey();
            Booking booking = new Booking(bookingID,bookingDate, bookingTime, user, instructor, false, "", "");
            reference.child("bookings").child(bookingID).setValue(booking);
            return booking;

        }catch (Exception ex){
            return null;
        }
    }
}
