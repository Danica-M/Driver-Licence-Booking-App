package com.example.nzta_booking_app.instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.adapters.TestAdapter;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Instructor_Tests extends AppCompatActivity {
    ArrayList<Booking> instructorTests;
    RecyclerView rv_test;
    TestAdapter rv_testAdapter;
    TextView schedTest;
    RecyclerView.LayoutManager rv_lm;
    String testDate;
    Controller controller;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_tests);

        controller = new Controller();
        instructorTests = new ArrayList<>();
        Intent intent = getIntent();
        testDate = intent.getStringExtra("date");

        schedTest = findViewById(R.id.scheDate);
        schedTest.setText("Scheduled Tests - " + testDate);
        rv_test = findViewById(R.id.recyclerTest);
        rv_test.setHasFixedSize(true);
        rv_lm = new LinearLayoutManager(this);
        rv_test.setLayoutManager(rv_lm);
        getScheduleTests();
    }


    //gets all the today's booked tests of the instructor who is logged in
    //and sets the recycler view  adapter
    public void getScheduleTests() {
        DatabaseReference bookingsRef = Controller.getReference().child("bookings");
        String fName = Controller.getCurrentInstructor().instructorFullName();
        Query query = bookingsRef.orderByChild("bookingTime");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                    Booking booking = bookingSnapshot.getValue(Booking.class);

                    if (booking != null && booking.getBookingInstructor().equals(fName) && booking.getBookingDate().equals(testDate)) {
                        instructorTests.add(booking);
                    }
                }
                rv_testAdapter = new TestAdapter(getApplicationContext(), instructorTests);
                rv_test.setAdapter(rv_testAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error Occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //returns to instructor's homepage
    public void home(View view) {
        Intent bIntent = new Intent(this, Instructor_Home.class);
        startActivity(bIntent);
    }
}