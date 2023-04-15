package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nzta_booking_app.adapters.HistoryAdapter;
import com.example.nzta_booking_app.adapters.TestAdapter;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class Instructor_Tests extends AppCompatActivity {
    ArrayList<Booking> instructorTests;
    RecyclerView rv_test;
    TestAdapter rv_testAdapter;
    RecyclerView.LayoutManager rv_lm;
    String testDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructor_tests);

        Intent intent = getIntent();
        testDate = intent.getStringExtra("date");

        rv_test = findViewById(R.id.recyclerTest);
        rv_test.setHasFixedSize(true);
        rv_lm = new LinearLayoutManager(this);
        rv_test.setLayoutManager(rv_lm);
        getScheduleTests();
    }


    public void getScheduleTests(){
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");
        String fName= Controller.getCurrentInstructor().instructorFullName();
        Query query = bookingsRef.orderByChild("bookingDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                instructorTests = new ArrayList<>();

                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                    Booking booking = bookingSnapshot.getValue(Booking.class);

                    if(booking != null && booking.getBookingInstructor().equals(fName) && booking.getBookingDate().equals(testDate)){
                        instructorTests.add(booking);
                    }
                }
                rv_testAdapter = new TestAdapter(getApplicationContext(), instructorTests);
                rv_test.setAdapter(rv_testAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}