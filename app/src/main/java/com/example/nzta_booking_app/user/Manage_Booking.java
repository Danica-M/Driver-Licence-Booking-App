package com.example.nzta_booking_app.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    Booking currentBooking;
    ArrayList<Booking> userBookings;
    TextView test_date, instructor, noCurrent, test_time;
    ImageView status_img;
    LinearLayout holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.manage_booking);
        bookBtn = findViewById(R.id.book);


        test_time = findViewById(R.id.test_time);
        test_date = findViewById(R.id.test_date);
        instructor = findViewById(R.id.txInstructor);
        status_img = findViewById(R.id.status_img);
        holder = findViewById(R.id.holder);
        noCurrent = findViewById(R.id.noCurrent);


        rv = findViewById(R.id.recyclerBook);
        rv.setHasFixedSize(true);
        rv_lm = new LinearLayoutManager(this);
        rv.setLayoutManager(rv_lm);
        getBookingHistory();

    }

    public void getBookingHistory() {

        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference().child("bookings");
        String fName = Controller.getCurrentUser().userFullName();
        Query query = bookingsRef.orderByChild("bookingDate");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userBookings = new ArrayList<>();
                currentBooking = null;
                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                    Booking booking = bookingSnapshot.getValue(Booking.class);

                    if (booking != null && booking.getBookingUser().equals(fName)) {
                        if (booking.getResulted()) {
                            userBookings.add(booking);
                        } else {
                            currentBooking = booking;
                        }
                    }
                }

                rv_adapter = new HistoryAdapter(getApplicationContext(), userBookings);
                rv.setAdapter(rv_adapter);
                if (currentBooking != null) {
                    bookBtn.setEnabled(false);
                    bookBtn.setBackgroundResource(R.drawable.plain_border);
                    noCurrent.setVisibility(View.GONE);
                    holder.setVisibility(View.VISIBLE);
                    test_date.setText("Date: " + currentBooking.getBookingDate());
                    test_time.setText("Time: " + currentBooking.getBookingTime());
                    instructor.setText("Instructor: " + currentBooking.getBookingInstructor());
                    status_img.setImageResource(R.drawable.booked);
                } else {
                    holder.setVisibility(View.GONE);
                    noCurrent.setVisibility(View.VISIBLE);
                }

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