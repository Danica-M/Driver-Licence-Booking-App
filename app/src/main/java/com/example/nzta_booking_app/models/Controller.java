package com.example.nzta_booking_app.models;

import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nzta_booking_app.user.Normal_Registration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractCollection;
import java.util.ArrayList;

public class Controller {
    private FirebaseDatabase firebaseDB;
    private static DatabaseReference reference;
    private static User currentUser;
    private static Instructor currentInstructor;
    private boolean bool;

    public Controller(){
        firebaseDB = FirebaseDatabase.getInstance();
        reference = firebaseDB.getReference();
        bool = false;
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
    public User registerUser(String userID, String fName, String lName, String licenceNum, String email, String password){
        try{
            User user = new User(userID,fName, lName,licenceNum,email,password);
            reference.child("users").child(userID).setValue(user);
            return user;

        }catch (Exception ex){
            return null;
        }
    }
    public Instructor registerInstructor(String instructorID,String fName, String lName, String licenceNum, String email, String password){
        try{
            Instructor instructor = new Instructor(instructorID,fName, lName, licenceNum, email, password);
            reference.child("instructors").child(instructorID).setValue(instructor);
            return instructor;

        }catch (Exception ex){
            return null;
        }
    }


    public Booking bookTest(String bookingDate, String bookingTime, String user, String userDl, String instructor){
        try{
            String bookingID = reference.push().getKey();
            Booking booking = new Booking(bookingID,bookingDate, bookingTime, user, userDl, instructor, false, "", "");
            reference.child("bookings").child(bookingID).setValue(booking);
            return booking;

        }catch (Exception ex){
            return null;
        }
    }

    public boolean userLicenceValidation(String dl){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int licenceCount = 0;
                for (DataSnapshot userItems : snapshot.getChildren()) {
                    User user = userItems.getValue(User.class);
                    if(user != null && user.getLicenceNum().equals(dl)){licenceCount++;}
                }
                if (licenceCount>0 && bool==false){
                    bool = false;
                }else{bool = true;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplication(), "Error Occured: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return bool;
    }

    public boolean instructorLicenceValidation(String dl){
        DatabaseReference instRef = firebaseDB.getReference().child("instructors");
        instRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int licenceCount = 0;
                for (DataSnapshot instItems : snapshot.getChildren()) {
                    Instructor instructor = instItems.getValue(Instructor.class);
                    if(instructor != null && instructor.getInstructorLicenceNum().equals(dl)){licenceCount++;}
                }
                if (licenceCount>0 && bool==false){
                    bool = false;
                }else{bool = true;}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplication(), "Error Occured: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return bool;
    }













}
