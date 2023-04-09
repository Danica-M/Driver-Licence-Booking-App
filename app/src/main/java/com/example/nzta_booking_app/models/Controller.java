package com.example.nzta_booking_app.models;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Controller {
    private FirebaseDatabase firebaseDB;
    private DatabaseReference reference;

    public Controller(){
        firebaseDB = FirebaseDatabase.getInstance();
        reference = firebaseDB.getReference();

    }
    public DatabaseReference getReference() {
        return reference;
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


    public Booking bookTest(String bookingDate, String bookingTime, String userID, String instructorID, Boolean isResulted, String results, String comments){
        try{
            String bookingID = reference.push().getKey();
            Booking booking = new Booking(bookingID,bookingDate, bookingTime, userID, instructorID, isResulted, results, comments);
            reference.child("bookings").child(bookingID).setValue(booking);
            return booking;

        }catch (Exception ex){
            return null;
        }
    }
}
