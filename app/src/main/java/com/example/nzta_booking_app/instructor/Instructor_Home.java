package com.example.nzta_booking_app.instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nzta_booking_app.Histogram;
import com.example.nzta_booking_app.Instructor_Tests;
import com.example.nzta_booking_app.Landing_Page;
import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.models.Instructor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Instructor_Home extends AppCompatActivity {
    TextView wMessage, tv_booked;
    Controller controller;
    FirebaseDatabase firebaseDB;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Booking> bookedTest = new ArrayList<>();
        firebaseDB = FirebaseDatabase.getInstance();

        Calendar calendar = Calendar.getInstance();
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String todayDate = sdf.format(calendar.getTime());
        instructorBookedTest(Controller.getCurrentInstructor().instructorFullName(),"14/04/2023");
        controller = new Controller();
        setContentView(R.layout.instructor_home);
        wMessage = findViewById(R.id.i_welMessage);
        tv_booked = findViewById(R.id.booked);
        wMessage.setText("Kia Ora, Instructor "+ Controller.getCurrentInstructor().instructorFullName());
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent bIntent = new Intent(this, Landing_Page.class);
        startActivity(bIntent);
    }

    public void instructorBookedTest(String instructorName, String bookingDate) {
        ArrayList<Booking> bookedTest = new ArrayList<>();
        Query query = firebaseDB.getReference("bookings");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot bookingItem : snapshot.getChildren()) {
                    Booking booking = bookingItem.getValue(Booking.class);
                    if (booking != null && booking.getBookingDate().equals(bookingDate) && booking.getBookingInstructor().equals(instructorName)){
                        bookedTest.add(booking);
                        Log.d("TAG","booking id "+booking.getBookingInstructor());
                        Log.d("TAG","booking id "+booking.getBookingDate());
                    }
                }
                tv_booked.setText("You have "+ bookedTest.size() + " test/s booked today!");
                Log.d("TAG","list id "+ bookedTest.size());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void graph(View view) {
        Intent bIntent = new Intent(this, Histogram.class);
        bIntent.putExtra("userType","instructor");
        startActivity(bIntent);
    }


    public void scheduledTests(View view) {
        Intent bIntent = new Intent(this, Instructor_Tests.class);
        bIntent.putExtra("date","14/04/2023");
        startActivity(bIntent);
    }



}