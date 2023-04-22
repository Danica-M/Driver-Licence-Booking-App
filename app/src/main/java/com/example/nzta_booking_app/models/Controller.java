package com.example.nzta_booking_app.models;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private FirebaseDatabase firebaseDB;
    private static DatabaseReference reference;

    private static SimpleDateFormat sdf;
    private static User currentUser;
    private static Instructor currentInstructor;


    public Controller() {
        firebaseDB = FirebaseDatabase.getInstance();
        reference = firebaseDB.getReference();
        sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    public static SimpleDateFormat getSdf(){return sdf;}
    public static DatabaseReference getReference() {
        return reference;
    }
    public static Instructor getCurrentInstructor() {
        return currentInstructor;
    }
    public static void setCurrentInstructor(Instructor instructor) {currentInstructor = instructor;}
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }

    //adds new user record to the database
    public User registerUser(String userID, String fName, String lName, String licenceNum, String email, String password) {
        try {
            User user = new User(userID, fName, lName, licenceNum, email, password);
            reference.child("users").child(userID).setValue(user);
            return user;

        } catch (Exception ex) {
            return null;
        }
    }

    //adds new instructor record to the database
    public Instructor registerInstructor(String instructorID, String fName, String lName, String licenceNum, String email, String password) {
        try {
            Instructor instructor = new Instructor(instructorID, fName, lName, licenceNum, email, password);
            reference.child("instructors").child(instructorID).setValue(instructor);
            return instructor;

        } catch (Exception ex) {
            return null;
        }
    }

    //adds new book record to the database
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

    //method to update the booking when its resulted
    public Booking resultTest(String bookingID, String bookingDate, String bookingTime, String user, String userDl, String instructor,Boolean isResulted, String results, String comments) {
        try {
            Booking booking = new Booking(bookingID, bookingDate, bookingTime, user, userDl, instructor, isResulted, results, comments);
            reference.child("bookings").child(bookingID).setValue(booking);
            return booking;

        } catch (Exception ex) {
            return null;
        }
    }


    // validates the licences length and format
    // first two should be letters and 3-8 should be number
    public static boolean validateLicence(String licence) {
        Pattern pattern = Pattern.compile("^[A-Za-z]{2}\\d{6}$");
        Matcher matcher = pattern.matcher(licence);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    // validation for firstname and lastname
    public static boolean validateString(String name) {
        Pattern pattern = Pattern.compile(".*\\d.*");
        Matcher matcher = pattern.matcher(name);
        if (matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }



    // returns all the sessions in strings
    public static ArrayList<String> getBookingSlots() {
        ArrayList<String> bookingSlots = new ArrayList<>();
        for (int i = 9; i < 17; i++) {
            String time = String.format(Locale.getDefault(), "%02d:00", i);
            String time1 = String.format(Locale.getDefault(), "%02d:30", i);
            bookingSlots.add(time);
            bookingSlots.add(time1);
        }
        return bookingSlots;
    }


    // gets all the instructor's names
    public static ArrayList<String> getInstructorsNames() {
        ArrayList<String> instructorNames = new ArrayList<>();
        reference.child("instructors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot instructorSnapshot : dataSnapshot.getChildren()) {
                    Instructor instructor = instructorSnapshot.getValue(Instructor.class);
                    instructorNames.add(instructor.instructorFullName());}
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        return instructorNames;
    }


}
