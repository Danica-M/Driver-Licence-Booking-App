package com.example.nzta_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.example.nzta_booking_app.user.Normal_Login;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Landing_Page extends AppCompatActivity {

    Controller controller;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        controller = new Controller();

        //optional! this method results all booking from past dates as failed and non-appearance
        autoResult();
    }
    public void getStarted(View view) {
        Intent intent = new Intent(this, Normal_Login.class);
        startActivity(intent);
    }

    public void autoResult() {
        DatabaseReference bookRef = Controller.getReference().child("bookings");
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot bookingItems:snapshot.getChildren()){
                    Booking booking = bookingItems.getValue(Booking.class);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    try{

                        if (booking != null) {
                            date = sdf.parse(booking.getBookingDate());
                        }

                        Date date2 = new Date();

                        if (date != null && booking != null) {
                            if(!(date.compareTo(date2)<0)){
                                controller.resultTest(booking.getBookingID(), booking.getBookingDate(), booking.getBookingTime(), booking.getBookingUser(), booking.getBookingUserDL(), booking.getBookingInstructor(), true, "failed", "non-appearance");
                            }

                        }
                    }catch(ParseException e) {
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
