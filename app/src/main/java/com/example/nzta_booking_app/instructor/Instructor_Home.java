package com.example.nzta_booking_app.instructor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nzta_booking_app.Histogram;
import com.example.nzta_booking_app.Landing_Page;
import com.example.nzta_booking_app.R;
import com.example.nzta_booking_app.models.Booking;
import com.example.nzta_booking_app.models.Controller;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class Instructor_Home extends AppCompatActivity {
    TextView wMessage, tv_booked;
    String todayDate;
    ArrayList<Booking> bookedTest;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookedTest = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        todayDate = Controller.getSdf().format(calendar.getTime());
        todayDate = "24/04/2023";
        instructorBookedTest(Controller.getCurrentInstructor().instructorFullName(), todayDate);
        setContentView(R.layout.instructor_home);
        wMessage = findViewById(R.id.i_welMessage);
        tv_booked = findViewById(R.id.booked);
        wMessage.setText("Kia Ora, Instructor " + Controller.getCurrentInstructor().instructorFullName());
    }


    public void logout(View view) {

        AlertDialog builder = new AlertDialog.Builder(Instructor_Home.this)
                .setTitle("Exit Confirmation")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent nlIntent = new Intent(Instructor_Home.this, Landing_Page.class);
                        startActivity(nlIntent);
                        finishAffinity();
                        FirebaseAuth.getInstance().signOut();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }

    public void instructorBookedTest(String instructorName, String bookingDate) {
        Query query = Controller.getReference().child("bookings");
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot bookingItem : snapshot.getChildren()) {
                    Booking booking = bookingItem.getValue(Booking.class);
                    if (booking != null && booking.getBookingDate().equals(bookingDate) && booking.getBookingInstructor().equals(instructorName)) {
                        bookedTest.add(booking);
                    }
                }
                tv_booked.setText("You have " + bookedTest.size() + " test/s booked today!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void graph(View view) {
        Intent bIntent = new Intent(this, Histogram.class);
        bIntent.putExtra("userType", "instructor");
        startActivity(bIntent);
    }


    public void scheduledTests(View view) {
        Intent bIntent = new Intent(this, Instructor_Tests.class);
        bIntent.putExtra("date", todayDate);
        startActivity(bIntent);
    }


}