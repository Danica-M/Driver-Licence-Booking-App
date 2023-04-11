package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nzta_booking_app.adapters.HistoryAdapter;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Manage_Booking extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.Adapter rv_adapter;
    RecyclerView.LayoutManager rv_lm;

    ArrayList<Booking> userBookings;
    FirebaseDatabase firebaseDB;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_booking);
        rv = findViewById(R.id.recyclerBook);
        rv.setHasFixedSize(true);
        rv_lm = new LinearLayoutManager(this);
        rv.setLayoutManager(rv_lm);
        getBookingHistory();

    }

    public void getBookingHistory(){
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
        String fName= Controller.getCurrentUser().userFullName();
        Query query = bookingsRef.orderByChild("bookingUser").equalTo(fName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userBookings = new ArrayList<>();

                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                    Booking booking = bookingSnapshot.getValue(Booking.class);
                    userBookings.add(booking);
                }
                rv_adapter = new HistoryAdapter(getApplicationContext(), userBookings);
                rv.setAdapter(rv_adapter);
                Log.d("TAG", "user: " + fName);
                Log.d("TAG", "booked: " + userBookings.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void bookTest(View view) {
        Intent bIntent = new Intent(this, Booking_Date_Selection.class);
        startActivity(bIntent);
    }

    public void home(View view) {
        Intent bIntent = new Intent(this, Normal_Home.class);
        startActivity(bIntent);
    }
}