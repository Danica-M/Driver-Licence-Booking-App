package com.example.nzta_booking_app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.nzta_booking_app.R;
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

public class Manage_Booking extends AppCompatActivity {

    RecyclerView rv;
    HistoryAdapter rv_adapter;
    RecyclerView.LayoutManager rv_lm;
    Button bookBtn;
    ArrayList<Booking> userBookings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBookings = new ArrayList<>();
        setContentView(R.layout.manage_booking);
        bookBtn = findViewById(R.id.book);


        rv = findViewById(R.id.recyclerBook);
        rv.setHasFixedSize(true);
        rv_lm = new LinearLayoutManager(this);
        rv.setLayoutManager(rv_lm);


//        getBookingHistory();
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
        String fName= Controller.getCurrentUser().userFullName();
        Query query = bookingsRef.orderByChild("bookingUser").equalTo(fName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                    Booking booking = bookingSnapshot.getValue(Booking.class);
                    userBookings.add(booking);
                }
                rv_adapter = new HistoryAdapter(getApplicationContext(), userBookings);
                rv.setAdapter(rv_adapter);
                if(!canUserBook(userBookings)){
                    bookBtn.setEnabled(false);
                }
                Log.d("TAG", "can book?:  " +canUserBook(userBookings).toString());
                Log.d("TAG", "can book?:  " +userBookings.size());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    public void getBookingHistory(){
//        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
//        String fName= Controller.getCurrentUser().userFullName();
//        Query query = bookingsRef.orderByChild("bookingUser").equalTo(fName);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
//                    Booking booking = bookingSnapshot.getValue(Booking.class);
//                    userBookings.add(booking);
//                }
//                rv_adapter = new HistoryAdapter(getApplicationContext(), userBookings);
//                rv.setAdapter(rv_adapter);
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }

    public Boolean canUserBook(ArrayList<Booking> bookings) {
        Boolean can = true;
        if (bookings.size()>=1) {
            Log.d("TAG", "book?:  " +userBookings.size());
            for (Booking book : bookings) {
                Log.d("TAG", "in loop: " + book.getResulted());
                if(book.getResulted()==false) {
                    Log.d("TAG", "res: " + book.getResulted());
                    can= false;
                }
            }
        }
        return can;
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